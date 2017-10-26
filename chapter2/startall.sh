#!/bin/bash

LOCATOR_PORT=10334
SERVER2_PORT=40405
PROJECT_JARS=../../projects/Domain/target/Domain-1.jar

. ../setenvironment.sh

gfsh <<!

start locator --name=locator --properties-file=config/locator.properties --bind-address=$HOSTNAME --port=$LOCATOR_PORT --J=-Xms256m --J=-Xmx256m --classpath=$GEMFIRE/locator-dependencies.jar

start server --name=server1 --locators=$HOSTNAME[$LOCATOR_PORT] --J=-Xms512m --J=-Xmx512m --classpath=$GEMFIRE/lib/server-dependencies.jar:$PROJECT_JARS --cache-xml-file=config/cache.xml --properties-file=config/gemfire.properties 

start server --name=server2 --locators=$HOSTNAME[$LOCATOR_PORT] --J=-Xms512m --J=-Xmx512m --classpath=$GEMFIRE/lib/server-dependencies.jar:$PROJECT_JARS --cache-xml-file=config/cache.xml --properties-file=config/gemfire.properties --server-port=$SERVER2_PORT

list members;

list regions;

exit;
!
