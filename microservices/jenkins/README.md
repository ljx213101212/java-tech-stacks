# Prerequisites

## Kubernetes Cluster

- A running Kubernetes cluster (local or remote).
- For local development, you can use tools like Minikube or kind.
- Ensure your `kubectl` is configured to interact with your cluster (`kubectl cluster-info`).

## `kubectl` Installed

- `kubectl` command-line tool installed on your machine.
- Version compatible with your Kubernetes cluster.

## Basic Knowledge

- Familiarity with Kubernetes resources: Pods, Deployments, Services, PersistentVolumes, PersistentVolumeClaims, and ConfigMaps.

# Overview of Steps

1. **Create a Namespace (Optional)**
2. **Set Up Persistent Storage for Jenkins Home**
3. **Create a Service Account and RBAC (Optional but Recommended)**
4. **Create ConfigMap for Jenkins Configuration (Optional)**
5. **Deploy Jenkins using a Deployment**
6. **Expose Jenkins via a Service**
7. **Apply All Configurations using `kubectl`**
8. **Access Jenkins Web Interface**

9. **Initiate Jenkins Agent Node**

---

## Step 1: Create a Namespace (Optional)
Creating a separate namespace for Jenkins isolates it from other applications.

### jenkins-namespace.yaml
```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: jenkins

```

## Step 2: Set Up Persistent Storage for Jenkins Home
Jenkins stores its configurations and job data in /var/jenkins_home. To persist this data, we need to set up persistent storage.

- Option A: Use Dynamic Provisioning with StorageClass (chosen)

If your cluster supports dynamic provisioning, you can create a PersistentVolumeClaim (PVC) without specifying a PersistentVolume (PV).  

### jenkins-pvc.yaml
```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: jenkins-pvc
  namespace: jenkins
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 10Gi

```
### Apply the PVC:

```yaml
kubectl apply -f jenkins-pvc.yaml
```
If dynamic provisioning isn't available, you can create a PV and PVC manually.  

- Option B: Manually Create a PersistentVolume (PV) and PVC

### jenkins-pv.yaml
```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: jenkins-pv
  namespace: jenkins
spec:
  capacity:
    storage: 10Gi
  accessModes:
    - ReadWriteOnce
  hostPath:
    path: /data/jenkins-pv
```
### jenkins-pvc.yaml
```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: jenkins-pvc
  namespace: jenkins
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 10Gi
```
### Apply PV and PVC:

```bash
kubectl apply -f jenkins-pv.yaml
kubectl apply -f jenkins-pvc.yaml
```
## Step 3: Create a Service Account and RBAC (Optional but Recommended)

For Jenkins to interact with the Kubernetes API (e.g., to deploy applications), it needs appropriate permissions.

### jenkins-service-account.yaml

```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: jenkins
  namespace: jenkins

```
### jenkins-cluster-role.yaml

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  name: jenkins-role
rules:
  - apiGroups: ["", "apps", "extensions"]
    resources: ["pods", "deployments", "services", "replicationcontrollers", "configmaps", "secrets"]
    verbs: ["create", "delete", "get", "list", "patch", "update", "watch"]

```
### jenkins-cluster-role-binding.yaml

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: jenkins-binding
subjects:
  - kind: ServiceAccount
    name: jenkins
    namespace: jenkins
roleRef:
  kind: ClusterRole
  name: jenkins-role
  apiGroup: rbac.authorization.k8s.io

```
### Apply Service Account and RBAC:

```yaml
kubectl apply -f jenkins-service-account.yaml
kubectl apply -f jenkins-cluster-role.yaml
kubectl apply -f jenkins-cluster-role-binding.yaml
```

## Step 4: Create ConfigMap for Jenkins Configuration (Optional)

You can use a ConfigMap to pass configuration options to Jenkins.
This step is optional and can be customized based on your needs.

## Step 5: Deploy Jenkins using a Deployment

Create a Deployment that defines the Jenkins Pod.

### jenkins-deployment.yaml
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: jenkins-deployment
  namespace: jenkins
spec:
  replicas: 1
  selector:
    matchLabels:
      app: jenkins
  template:
    metadata:
      labels:
        app: jenkins
    spec:
      serviceAccountName: jenkins
      containers:
        - name: jenkins
          image: jenkins/jenkins:lts
          ports:
            - containerPort: 8080
            - containerPort: 50000
          volumeMounts:
            - name: jenkins-home
              mountPath: /var/jenkins_home
      volumes:
        - name: jenkins-home
          persistentVolumeClaim:
            claimName: jenkins-pvc

```
### Apply the Deployment:

```bash
kubectl apply -f jenkins-deployment.yaml
```

## Step 6: Expose Jenkins via a Service

Create a Service to expose Jenkins to external traffic.

### Option A: NodePort Service

#### jenkins-service.yaml

```yaml
apiVersion: v1
kind: Service
metadata:
  name: jenkins-service
  namespace: jenkins
spec:
  type: NodePort
  ports:
    - port: 8080
      targetPort: 8080
      nodePort: 30000
  selector:
    app: jenkins

```

### Option B: LoadBalancer Service (If your cluster supports it)  

Ensure minikube tunnel Is Running 

```bash
minikube tunnel
```

```yaml
apiVersion: v1
kind: Service
metadata:
  name: jenkins-service
  namespace: jenkins
spec:
  type: LoadBalancer
  ports:
    - port: 8080
      targetPort: 8080
  selector:
    app: jenkins

```



### Option C: Ingress Controller
If you have an Ingress controller set up, you can create an Ingress resource to route traffic.

#### jenkins-ingress.yaml

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: jenkins-ingress
  namespace: jenkins
  annotations:
    kubernetes.io/ingress.class: nginx
spec:
  rules:
    - host: jenkins.example.com
      http:
        paths:
          - path: /
            pathType: Prefix
            backend:
              service:
                name: jenkins-service
                port:
                  number: 8080

```

#### Apply the Service and Ingress:

```bash
kubectl apply -f jenkins-service.yaml
# If using Ingress
kubectl apply -f jenkins-ingress.yaml

```

## Step 7: Apply All Configurations

Ensure that all YAML files are applied to your cluster.

### Option: Apply All at Once

If all your YAML files are in a directory called jenkins-k8s, you can apply them all:
```bash
kubectl apply -f jenkins-k8s/
```


## Step 8: Access Jenkins Web Interface

### Using NodePort Service
#### Get the Node IP:

```bash
kubectl get nodes -o wide
```
Note the external IP of one of the nodes.

#### Access Jenkins:

Navigate to http://<node-ip>:30000 in your web browser.

### Using LoadBalancer Service
#### Get the External IP:

```bash
kubectl get service jenkins-service -n jenkins
```
Wait until the EXTERNAL-IP is assigned.

#### Access Jenkins:

Navigate to http://<external-ip>:8080.

#### Using Ingress
- Ensure DNS is set up to point jenkins.example.com to your Ingress controller's IP.
- Access Jenkins via http://jenkins.example.com.

## Step 9: Unlock Jenkins
When you first access Jenkins, it will prompt you to enter an initial admin password.

#### Get the Initial Admin Password:
```bash
kubectl exec --namespace jenkins -it $(kubectl get pods --namespace jenkins -l app=jenkins -o jsonpath="{.items[0].metadata.name}") -- cat /var/jenkins_home/secrets/initialAdminPassword

```

#### Copy the Password:
Paste it into the Jenkins web interface to continue the setup.

#### Complete Jenkins Setup:
- Install suggested plugins or select plugins manually.
- Create your first admin user.
- Finish configuration.


## Step 10. Initiate Jenkins Agent Node

### Adding a New Agent Node Manually via Jenkins UI
1. Go to Jenkins Dashboard.
2. Manage Jenkins > Manage Nodes and Clouds > New Node.
3. Enter a Node Name such as jdk22-maven3-agent and select Permanent Agent.
4. Configure the node:
- Remote root directory: /home/jenkins or another suitable directory where Jenkins will store files.
- Labels: Add the label jdk22-maven3-agent to identify this agent.
- Usage: Choose either "Only build jobs with label expressions matching this node" or "Use this node as much as possible," depending on how you want the node to be used.
- Launch method:
  - Launch agent by SSH: If the agent is a remote machine, enter the SSH details.
  - Launch agent via Java Web Start: This can be used if you want to start the agent manually.
5. Save the node configuration.
6. Follow the page instruction and start the agent

```bash
curl -sO http://localhost:8080/jnlpJars/agent.jar
sudo apt install openjdk-21-jre-headless
sudo java -jar agent.jar -url http://localhost:8080/ -secret 9deaae34bbb1618fa99e36ad17912fe3cefbfe9c9b37b6a46d5e6d67e6cd55cd -name "jdk22-maven3-agent" -webSocket -workDir "/home/jenkins"s
```
7. You will see the agent is online.




















