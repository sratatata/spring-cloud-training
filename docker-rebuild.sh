#!/bin/sh
# clean
docker rmi $(docker images | grep "^training")
docker rmi $(docker images | grep "<none>")
#build
docker build -t "training/configuration" configuration
docker build -t "training/discovery" discovery
docker build -t "training/gateway" gateway
docker build -t "training/users" users
docker build -t "training/departments" departments
docker build -t "training/client" client
docker build -t "training/zipkin" zipkin