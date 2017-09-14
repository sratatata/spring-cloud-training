#!/bin/sh
while ! `nc -z configuration_server 8001`;
    do sleep 5;echo "Waiting for configuration server to start...";done
java -jar -Dspring.cloud.config.uri=$CONFIGURATION_SERVER_URI /usr/local/service/discovery.jar