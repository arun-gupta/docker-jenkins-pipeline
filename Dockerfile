From uday/jenkins-git
MAINTAINER Udaypal Aarkoti <uaarkoti@gmail.com>

ENV JENKINS_HOME /var/lib/jenkins

# install git plugin and related dependencies
ADD jobs /var/lib/jenkins/jobs

RUN chown -R jenkins:jenkins /var/lib/jenkins

CMD service sshd start ; service jenkins start ; bash

EXPOSE 22 8080
