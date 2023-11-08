#!/bin/bash

export HOST_UID_GID=$JENKINS_USER_ID:$JENKINS_GROUP_ID

# JOB_NAME is the name of the project of this build. This is the name you gave your job. It is set up by Jenkins

#COMPOSE_ID=${JOB_NAME}
#echo $COMPOSE_ID
#
## Remove Previous Stack
#docker-compose -p $COMPOSE_ID rm -f
#
## Starting new stack environment
#docker-compose -p $COMPOSE_ID -f docker-compose-api.yaml up -d --no-color --build
#
#echo "* UP - sleeping for 180s.. check containers and target folder"
#sleep 180s
#
#docker-compose -p $COMPOSE_ID -f docker-compose-api.yaml run -e TYPE="@API" -u ${HOST_UID_GID} api-test-service
#
#echo "* RUN - sleeping AGAIN for 180s.. check containers and target folder"
#sleep 180s
#
##docker-compose -p $COMPOSE_ID -f docker-compose-api.yaml run -e TYPE="@API" -u ${HOST_UID_GID} api-test-service --name "api"
##docker exec api bash
##chown -R $JENKINS_USER_ID:$JENKINS_GROUP_ID /home/jenkins
##exit
#
#echo "Project Name - service - status.."
#docker-compose -p $COMPOSE_ID ps -a


# Remove Previous Stack
docker-compose rm -f

# Starting new stack environment
docker-compose -f docker-compose-api.yaml up -d --no-color --build

echo "* UP - sleeping for 180s.. check containers and target folder"
sleep 180s

docker-compose -f docker-compose-api.yaml run -e TYPE="@API" -u ${HOST_UID_GID} api-test-service

echo "* RUN - sleeping AGAIN for 180s.. check containers and target folder"
sleep 180s

#docker-compose -p $COMPOSE_ID -f docker-compose-api.yaml run -e TYPE="@API" -u ${HOST_UID_GID} api-test-service --name "api"
#docker exec api bash
#chown -R $JENKINS_USER_ID:$JENKINS_GROUP_ID /home/jenkins
#exit

echo "service - status.."
docker-compose ps -a