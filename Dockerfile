FROM ubuntu:latest

ARG GID
ARG UID

ENV GROUP_ID=$GID
ENV USER_ID=$UID
ENV USERNAME=jenkins

RUN mkdir /home/$USERNAME

WORKDIR /home/$USERNAME
COPY src /home/$USERNAME/src
COPY pom.xml /home/$USERNAME
COPY runner-api.sh /home/$USERNAME

USER root

#Jenkins user and permissions
RUN groupadd -g $GROUP_ID $USERNAME
RUN useradd -m -u $USER_ID -g $USERNAME -d /home/$USERNAME $USERNAME
RUN chown -R $USERNAME:$USERNAME /home/$USERNAME
RUN chmod -R ug+rwx /home/$USERNAME
#RUN mkdir -p /var/jenkins_home
#RUN chown -R $USERNAME:$USERNAME /var/jenkins_home
#RUN chmod -R ug+rwx /var/jenkins_home

#permission to connect to the Docker daemon socket
#RUN chown -R "$USERNAME":"$USERNAME" /var/run/docker.sock \
#RUN chmod -R ug+rwx /var/run/docker.sock \
#RUN chown -R "$USERNAME":"$USERNAME" /home/"$USERNAME"/.docker \
#RUN chmod -R ug+rwx "$HOME/.docker"

RUN usermod -aG sudo $USERNAME

# Create a runner script for the entrypoint (used in docker-compose)
RUN chown -R $USERNAME:$USERNAME ./runner-api.sh
RUN chmod ug+x ./runner-api.sh
RUN mkdir -p target && chown -R $USERNAME:$USERNAME target && chmod -R ug+rwx target

#Basic Utils
RUN apt-get update
RUN apt-get install -y wget curl jq unzip sudo tar acl apt-transport-https ca-certificates software-properties-common --no-install-recommends

#Java
RUN apt-get install -y openjdk-17-jdk

#Maven
ENV MAVEN_VERSION 3.9.5
RUN wget --no-check-certificate https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz
RUN mkdir -p /opt/maven
RUN tar -xvzf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt/maven/ --strip-components=1
RUN ln -s /opt/maven/bin/mvn /usr/bin/mvn
RUN rm -f apache-maven-${MAVEN_VERSION}-bin.tar.gz

RUN chown -R $USERNAME:$USERNAME /usr/bin/mvn
RUN chmod -R ug+rwx /usr/bin/mvn

#Docker - https://docs.docker.com/engine/api/
ENV DOCKER_CHANNEL stable
ENV DOCKER_VERSION 23.0.6
ENV DOCKER_API_VERSION 1.42
RUN curl -k -fsSL "https://download.docker.com/linux/static/${DOCKER_CHANNEL}/x86_64/docker-${DOCKER_VERSION}.tgz" \
  | tar -xzC /usr/local/bin --strip=1 docker/docker

RUN groupadd docker
RUN usermod -aG docker $USERNAME


#Docker compose - https://docs.docker.com/compose/release-notes/
ENV DOCKER_COMPOSE_VERSION v2.23.0
RUN curl -k -fsSL "https://github.com/docker/compose/releases/download/${DOCKER_COMPOSE_VERSION}/docker-compose-linux-x86_64" \
    -o /usr/local/bin/docker-compose

#RUN curl -fsSL "https://sourceforge.net/projects/docker-compose.mirror/files/${DOCKER_COMPOSE_VERSION}/docker-compose-linux-x86_64/download"  \
#    -o /usr/local/bin/docker-compose

RUN chown -R $USERNAME:$USERNAME /usr/local/bin/docker-compose
RUN chmod ug+x /usr/local/bin/docker-compose
#RUN ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose

#RUN chown -R $USERNAME:$USERNAME /usr/local/bin/docker-compose
#RUN chmod -R ug+rwx /usr/local/bin/docker-compose
#RUN chown -R $USERNAME:$USERNAME /usr/bin/docker-compose
#RUN chmod -R ug+rwx /usr/bin/docker-compose

USER $USERNAME