
FROM jenkins/jenkins:jdk17
#FROM jenkins/jenkins:lts
LABEL maintainer="ash"

WORKDIR /home/docker-jenkins-test
COPY src /home/docker-jenkins-test/src
COPY pom.xml /home/docker-jenkins-test

ENV JAVA_OPTS="-Xmx8192m"
ENV JENKINS_OPTS="--logfile=/var/log/jenkins/jenkins.log"

USER root

# Create a runner script for the entrypoint (used in docker-compose)
COPY runner-api.sh  /home/docker-jenkins-test
RUN chmod +x ./runner-api.sh

#Basic Utils
RUN apt-get update
RUN apt-get install -y wget curl jq unzip sudo tar acl --no-install-recommends

#Maven
ENV MAVEN_VERSION 3.9.5
RUN wget https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz
RUN mkdir -p /opt/maven
RUN tar -xvzf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt/maven/ --strip-components=1
RUN ln -s /opt/maven/bin/mvn /usr/bin/mvn
RUN rm -f apache-maven-${MAVEN_VERSION}-bin.tar.gz

RUN mkdir /var/log/jenkins
RUN chown -R  jenkins:jenkins /var/log/jenkins

#Docker - https://docs.docker.com/engine/api/
ENV DOCKER_CHANNEL stable
ENV DOCKER_VERSION 23.0.6
ENV DOCKER_API_VERSION 1.42
RUN curl -fsSL "https://download.docker.com/linux/static/${DOCKER_CHANNEL}/x86_64/docker-${DOCKER_VERSION}.tgz" \
  | tar -xzC /usr/local/bin --strip=1 docker/docker

#Docker compose - https://docs.docker.com/compose/release-notes/
ENV DOCKER_COMPOSE_VERSION v2.22.0
RUN curl -fsSL "https://github.com/docker/compose/releases/download/${DOCKER_COMPOSE_VERSION}/docker-compose-linux-x86_64" \
    -o /usr/local/bin/docker-compose

#RUN curl -fsSL "https://sourceforge.net/projects/docker-compose.mirror/files/${DOCKER_COMPOSE_VERSION}/docker-compose-linux-x86_64/download"  \
#    -o /usr/local/bin/docker-compose

RUN chmod +x /usr/local/bin/docker-compose

RUN groupadd docker
RUN usermod -aG docker jenkins
RUN usermod -aG sudo jenkins

RUN chown -R jenkins:jenkins /home/docker-jenkins-test/
RUN chmod -R ug+rwx /home/docker-jenkins-test/
RUN mkdir -p target && chown -R jenkins:jenkins target && chmod -R ug+rwx target

USER jenkins