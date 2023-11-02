def JENKINS_USER_ID
def JENKINS_GROUP_ID
node {
    JENKINS_USER_ID = sh(returnStdout: true, script: 'id -u').trim()
    JENKINS_GROUP_ID = sh(returnStdout: true, script: 'id -g').trim()
}

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
        stage('Check uid/gid') {
            steps {
                echo 'JENKINS_USER_ID'
                echo JENKINS_USER_ID
                echo 'JENKINS_GROUP_ID'
                echo JENKINS_GROUP_ID
            }
        }
		stage('Build') {
            agent {
                dockerfile {
                    filename 'Dockerfile'
                    args '-u 0:0'
/*                    additionalBuildArgs '''\
                        --build-arg GID=1001 \
                        --build-arg UID=1001 \
                        '''*/
                    reuseNode true
                }
            }
            steps {
                //echo "${JENKINS_USER_NAME}"
                echo "${JENKINS_USER_ID}"
                echo "${JENKINS_GROUP_ID}"

                script {
                    currentWorkspace = "$WORKSPACE"
                    echo "Current workspace is ${currentWorkspace}"
                }
                echo "Check Docker version.."
                sh '''
				  echo "Check permissions 1"
				  sh 'ls -lrt'
				  sh 'whoami'
                '''
            }
        }

        stage('Build Dependencies') {
            steps {
                sh "Install dependencies"
            }
        }
	}

    post {
        always {
            sh "Job finished"
        }
    }
}