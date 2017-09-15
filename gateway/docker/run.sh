#!/bin/sh
while ! `nc -z configuration_server $CONFIGURATION_SERVER_PORT`;
    do sleep 5;echo "Waiting for configuration server to start...";done
while ! `nc -z discovery_server $DISCOVERY_SERVER_PORT`;
    do sleep 5;echo "Waiting for discovery server to start...";done
java -jar -Dspring.cloud.config.uri=$CONFIGURATION_SERVER_URI /usr/local/service/gateway.jar