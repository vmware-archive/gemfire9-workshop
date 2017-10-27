#!/bin/bash

LOCATOR_PORT=10334
SERVER2_PORT=40405
PROJECT_JARS=../../projects/Domain/target/Domain-1.jar

. ../setenvironment.sh

gfsh <<!

start locator --name=locator --properties-file=config/locator.properties --bind-address=$HOSTNAME --port=$LOCATOR_PORT --J=-Xms256m --J=-Xmx256m


start server --name=server1 --locators=$HOSTNAME[$LOCATOR_PORT] --J=-Xms512m --J=-Xmx512m --classpath=$PROJECT_JARS --cache-xml-file=config/cache.xml --properties-file=config/gemfire.properties --J=-Dgemfire.start-dev-rest-api=true --J=-Dgemfire.http-service-bind-address=$IP --J=-Dgemfire.http-service-port=7075

start server --name=server2 --locators=$HOSTNAME[$LOCATOR_PORT] --J=-Xms512m --J=-Xmx512m --classpath=$PROJECT_JARS --cache-xml-file=config/cache.xml --properties-file=config/gemfire.properties --server-port=$SERVER2_PORT

undeploy --jar=GemServer-1.jar
deploy --jar=../projects/GemServer/target/GemServer-1.jar

list members;

list regions;

exit;
!

