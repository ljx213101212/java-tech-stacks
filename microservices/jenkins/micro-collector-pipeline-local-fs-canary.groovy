pipeline {
    agent  {
        label 'jdk22-maven3-agent'
    }

    environment {
        MICRO_COLLECTOR_DIR = '/mnt/c/Work/elearning/java-tech-stacks/microservices/micro-collector'
        KUBECONFIG = credentials('kubeconfig-file-id')
        VERSION = 'latest'  // Use the 'latest' tag to ensure the just-built image is used
        BRANCH = 'dev'
        DOCKER_IMAGE = 'ljx213101212/micro-collector-k8s'  // Ensure the Docker image tag matches the built image
    }

    stages {
        stage('Checkout') {
            steps {
                dir("${MICRO_COLLECTOR_DIR}") {
                    sh "git checkout ${BRANCH}"
                }
            }
        }

        stage('Build') {
            steps {
                dir("${MICRO_COLLECTOR_DIR}") {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                dir("${MICRO_COLLECTOR_DIR}") {
                    sh 'mvn test'
                }
            }
        }

        stage('Package') {
            steps {
                dir("${MICRO_COLLECTOR_DIR}") {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t ${DOCKER_IMAGE}:latest -f ${MICRO_COLLECTOR_DIR}/Dockerfile-k8s ${MICRO_COLLECTOR_DIR}/.'
            }
        }

        stage('Create Ingress') {
            steps {
                script {
                    def ingressExists = sh(script: "kubectl get ingress micro-collector-ingress --kubeconfig=${KUBECONFIG}", returnStatus: true)
                    if (ingressExists != 0) {
                        sh '''
                        kubectl apply -f ${MICRO_COLLECTOR_DIR}/ingress.yaml --kubeconfig=${KUBECONFIG}
                        '''
                    }
                }
            }
        }

        stage('Deploy Canary Version') {
            steps {
                script {
                    def containerName = sh(script: "kubectl get deployment micro-collector-canary -o=jsonpath='{.spec.template.spec.containers[0].name}' --kubeconfig=${KUBECONFIG}", returnStdout: true).trim()
                    sh "kubectl set image deployment/micro-collector-canary ${containerName}=${DOCKER_IMAGE}:${VERSION} --kubeconfig=${KUBECONFIG}"
                }
            }
        }

        stage('Increase Traffic to Canary') {
            steps {
                script {
                    def weights = [10, 30, 50, 100]
                    for (weight in weights) {
                        sh "kubectl patch ingress micro-collector-ingress -p '{\"metadata\": {\"annotations\": {\"nginx.ingress.kubernetes.io/canary-weight\": \"${weight}\"}}}' --kubeconfig ${KUBECONFIG}"
                        sleep time: 5, unit: 'SECONDS'  // Pause for 5 seconds for showing the concept
                    }
                }
            }
        }


        stage('Scale Stable and Canary Deployments') {
            steps {
                sh '''
                kubectl scale deployment micro-collector --replicas=0 --kubeconfig ${KUBECONFIG}
                kubectl scale deployment micro-collector-canary --replicas=3 --kubeconfig ${KUBECONFIG}
                '''
            }
        }
    }

    post {
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed.'
        }
    }
}
