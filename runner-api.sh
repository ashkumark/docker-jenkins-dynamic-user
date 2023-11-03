#!/bin/sh

#version 1
echo "Run automated API tests (using runner script)..."
#mvn -f pom.xml test -Dtest=TestRunner -Dcucumber.filter.tags=$TYPE
mvn test -Dtest=TestRunner -Dcucumber.filter.tags=$TYPE
#mvn test -Dcucumber.filter.tags=$TYPE
echo "API tests run completed..."

#version 2 - copy target from container to host
sleep 3s
echo "Check permissions in runner"
pwd
ls -lrt
whoami
cd target/
ls -lrt

#version 3
##install docker
#sleep 2s
#echo "Installing docker"
#apt-get update
#apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin --no-install-recommends
#docker version
#
#echo "Copy target from docker container to workspace"
#docker cp api-test-container:/home/docker-jenkins-test/target/ ${currentWorkspace}/reports/