#!/bin/sh
while ! `nc -z configuration_server $DEFAULT_PORT`;
    do sleep $PING_TIMEOUT;echo "Waiting for configuration server to start...";done
while ! `nc -z discovery_server $DEFAULT_PORT`;
    do sleep $PING_TIMEOUT;echo "Waiting for discovery server to start...";done
while ! `nc -z departments_database 5432`;
    do sleep $PING_TIMEOUT;echo "Waiting for departments database to start...";done
java -jar -Dspring.profiles.active=$DEFAULT_PROFILE -Dspring.cloud.config.uri=$CONFIGURATION_SERVER_URI /usr/local/service/departments.jar