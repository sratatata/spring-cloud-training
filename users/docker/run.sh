#!/bin/sh
while ! `nc -z configuration_server $DEFAULT_PORT`;
    do sleep $PING_TIMEOUT;echo "Waiting for configuration server to start...";done
while ! `nc -z discovery_server $DEFAULT_PORT`;
    do sleep $PING_TIMEOUT;echo "Waiting for discovery server to start...";done
while ! `nc -z users_database 5432`;
    do sleep $PING_TIMEOUT;echo "Waiting for users database to start...";done
java -jar -Dcloud.stream.kafka.binder.zkNodes=$KAFKA_SERVER -Dcloud.stream.kafka.binder.brokers=$KAFKA_SERVER -Dspring.profiles.active=$DEFAULT_PROFILE -Dspring.cloud.config.uri=$CONFIGURATION_SERVER_URI /usr/local/service/users.jar