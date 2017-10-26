#!/bin/bash

LOCATOR_PORT=10334
SERVER2_PORT=40405
PROJECT_JARS=../../../projects/Domain/target/Domain-1.jar

. ../../setenvironment.sh

gfsh <<!

start locator --name=locator1 --properties-file=config/locator.properties --bind-address=$HOSTNAME --port=$LOCATOR_PORT --dir=locator1 --J=-Xms256m --J=-Xmx256m --classpath=$GEMFIRE/locator-dependencies.jar

start server --name=server1 --locators=$HOSTNAME[$LOCATOR_PORT] --dir=server1 --J=-Xms512m --J=-Xmx512m --classpath=$GEMFIRE/lib/server-dependencies.jar:$PROJECT_JARS --cache-xml-file=config/cache.xml --properties-file=config/gemfire.properties --J=-Dgemfire.ALLOW_PERSISTENT_TRANSACTIONS=true


start server --name=server2 --locators=$HOSTNAME[$LOCATOR_PORT] --dir=server2 --J=-Xms512m --J=-Xmx512m --classpath=$GEMFIRE/lib/server-dependencies.jar:$PROJECT_JARS --cache-xml-file=config/cache.xml  --properties-file=config/gemfire.properties --server-port=$SERVER2_PORT --J=-Dgemfire.ALLOW_PERSISTENT_TRANSACTIONS=true

list members;

list regions;

exit;
!
