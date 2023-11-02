FROM maven:3.8.3-openjdk-17

ARG GID
ARG UID
ARG UNAME

ENV GROUP_ID=$GID
ENV USER_ID=$UID
ENV USERNAME=jenkins

RUN mkdir /home/$USERNAME

RUN groupadd -g $GROUP_ID $USERNAME
RUN useradd -r -u $USER_ID -g $GROUP_ID -d /home/$USERNAME $USERNAME
RUN chown $USERNAME:$USERNAME /home/$USERNAME

RUN groupadd docker
RUN usermod -aG docker $USERNAME
RUN usermod -aG sudo $USERNAME

USER $USERNAME
WORKDIR /home/$USERNAME
COPY src /home/$USERNAME/src
COPY pom.xml /home/$USERNAME

