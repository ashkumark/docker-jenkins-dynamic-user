/*def JENKINS_USER_ID
def JENKINS_GROUP_ID
node {
    JENKINS_USER_ID = sh(returnStdout: true, script: 'id -u').trim()
    JENKINS_GROUP_ID = sh(returnStdout: true, script: 'id -g').trim()
}*/

pipeline {
    agent any
    environment {
		/*JENKINS_USER_NAME = "${sh(script:'id -un', returnStdout: true).trim()}"
		JENKINS_USER_ID = "${sh(script:'id -u', returnStdout: true).trim()}"
		JENKINS_GROUP_ID = "${sh(script:'id -g', returnStdout: true).trim()}"*/
		currentWorkspace = ''
    }

	options {
        timeout(time: 5, unit: 'MINUTES')
    }

    stages {
		stage('Build') {
            agent {
                dockerfile {
                    filename 'Dockerfile'
/*                    additionalBuildArgs '''\
                        --build-arg GID=$JENKINS_USER_ID \
                        --build-arg UID=$JENKINS_GROUP_ID \
                        '''*/
                    reuseNode true
                }
            }
            steps {
/*                //echo "${JENKINS_USER_NAME}"
                echo "${JENKINS_USER_ID}"
                echo "${JENKINS_GROUP_ID}"*/

                script {
                    currentWorkspace = "$WORKSPACE"
                    echo "Current workspace is ${currentWorkspace}"
                }
                echo "Check Docker version.."
                sh '''#!/bin/bash
                      echo "Check permissions 1"
                      ls -lrt
                      docker version
                      docker-compose version
                '''
            }
        }

        stage('Build Dependencies') {
            steps {
                echo "Install dependencies"
            }
        }
	}

    post {
        always {
            echo "Job finished"
        }
    }
}