pipeline {
    environment {
		JENKINS_USER_NAME = "${sh(script:'id -un', returnStdout: true).trim()}"
		JENKINS_USER_ID = "${sh(script:'id -u', returnStdout: true).trim()}"
		JENKINS_GROUP_ID = "${sh(script:'id -g', returnStdout: true).trim()}"
		currentWorkspace = ''
    }
	
	options {
        timeout(time: 5, unit: 'MINUTES')
    }
	
    agent {
        dockerfile {
            filename 'Dockerfile'
            additionalBuildArgs '''\
            --build-arg GID=$JENKINS_GROUP_ID \
            --build-arg UID=$JENKINS_USER_ID \
            --build-arg UNAME=$JENKINS_USER_NAME \
            '''
        }
    }

    stages {
		stage('Check tooling versions') {
            steps {
                script {
                    currentWorkspace = "$WORKSPACE"
                    echo "Current workspace is ${currentWorkspace}"
                }
                echo "Check Docker version.."
                sh '''
				  echo "Check permissions 1"
				  sh 'ls -lrt'
				  sh 'whoami'
                  docker version
                  docker info
                  docker-compose version
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