pipeline {
    agent  {
        label 'jdk22-maven3-agent'
    }

    environment {
        MICRO_COLLECTOR_DIR = '/mnt/c/Work/elearning/java-tech-stacks/microservices/micro-collector'
        BRANCH = 'dev'
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
                sh 'docker build -t ljx213101212/micro-collector-k8s:latest -f ${MICRO_COLLECTOR_DIR}/Dockerfile-k8s ${MICRO_COLLECTOR_DIR}/.'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withCredentials([file(credentialsId: 'kubeconfig-file-id', variable: 'KUBECONFIG')]) {
                    sh 'kubectl apply -f ${MICRO_COLLECTOR_DIR}/deployment-k8s.yaml'
                }
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
