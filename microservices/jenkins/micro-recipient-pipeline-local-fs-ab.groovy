pipeline {
    agent { label 'jdk22-maven3-agent' }

    environment {
        MICRO_RECIPIENT_DIR = '/mnt/c/Work/elearning/java-tech-stacks/microservices/micro-recipient'
        TMP_JENKINS_FOLDER = '/mnt/c/Work/elearning/java-tech-stacks/microservices/jenkins/ab_test'
        IMAGE_NAME = 'ljx213101212/micro-recipient-k8s'
        DOCKER_FILE_NAME = 'Dockerfile'
    }

    stages {
        stage('Build and Deploy') {
            matrix {
                //https://www.jenkins.io/doc/book/pipeline/syntax/#declarative-matrix
                axes {
                    axis {
                        name 'BRANCH'
                        values 'a_b_testing_v1', 'a_b_testing_v2', 'a_b_testing_v3'
                    }
                }
                stages {
                    stage('Checkout') {
                        steps {
                            script {
                                // Define a unique workspace directory for each branch
                                def workDir = "${env.TMP_JENKINS_FOLDER}/${BRANCH}"
                                sh """
                                    mkdir -p '${workDir}'
                                """
                                //copy everything except jenkins folder itself into jenkins folder (simulate git clone source code from different branches)
                                dir(workDir) {
                                    sh """
                                        rsync -av --exclude='${env.TMP_JENKINS_FOLDER}' --exclude="${workDir}" "${env.MICRO_RECIPIENT_DIR}/" "${workDir}"
                                    """
                                }
                            }
                        }
                    }
                    stage('Build') {
                        steps {
                            script {
                                def workDir = "${env.TMP_JENKINS_FOLDER}/${BRANCH}"
                                dir(workDir) {
                                    sh 'mvn clean package -DskipTests'
                                }
                            }
                        }
                    }
                    stage('Test') {
                        steps {
                            script {
                                def workDir = "${env.TMP_JENKINS_FOLDER}/${BRANCH}"
                                dir(workDir) {
                                    sh 'mvn test'
                                }
                            }
                        }
                    }
                    stage('Package') {
                        steps {
                            script {
                                def workDir = "${env.TMP_JENKINS_FOLDER}/${BRANCH}"
                                dir(workDir) {
                                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                                }
                            }
                        }
                    }
                    stage('Docker Build') {
                        steps {
                            script {
                                def workDir = "${env.TMP_JENKINS_FOLDER}/${BRANCH}"
                                dir(workDir) {
                                    // [TODO]: minikube cannot run in jenkins env, need to check,  run below in WSL terminal to do manual build for now.
                                    //The eval $(minikube docker-env) command sets your terminal to use Minikube’s Docker daemon.
                                    sh """
                                        docker build -t ${env.IMAGE_NAME}:${BRANCH} \\
                                        -f ${env.DOCKER_FILE_NAME} \\
                                        .
                                    """
                                }
                            }
                        }
                    }
                    stage('Deploy to Kubernetes') {
                        steps {
                            script {
                                def workDir = "${env.TMP_JENKINS_FOLDER}/${BRANCH}"
                                dir(workDir) {
                                    withCredentials([file(credentialsId: 'kubeconfig-file-id', variable: 'KUBECONFIG')]) {
                                        sh "kubectl apply -f deployment_k8s_${BRANCH}.yaml"
                                    }
                                }
                            }
                        }
                    }
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