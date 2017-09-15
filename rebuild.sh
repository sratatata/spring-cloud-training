#!/bin/sh
mvn clean
mvn package
cd client
rm -rf target
ng build --prod