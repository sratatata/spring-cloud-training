FROM openjdk:8-jdk-alpine
RUN apk update && apk upgrade
RUN mkdir -p /usr/local/service
ADD docker/jce_policy-8.zip /tmp
RUN cd /tmp/ && \
    unzip jce_policy-8.zip && \
    rm jce_policy-8.zip && \
    yes |cp -v /tmp/UnlimitedJCEPolicyJDK8/*.jar /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
ADD target/configuration.jar /usr/local/service/
ADD docker/run.sh run.sh
RUN chmod +x run.sh
CMD /bin/sh run.sh