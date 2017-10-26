@echo off
rem assign different ports than those of data center 2
set LOCATOR_PORT=10335
set SERVER1_PORT=40406
set SERVER2_PORT=40407

set PROJECT_JARS=..\..\..\projects\Domain\target\Domain-1.jar

call ..\..\setenvironment.bat

set START_LOCATOR="start locator --name=locator2 --properties-file=config\locator.properties --bind-address=%IP% --port=%LOCATOR_PORT% --J=-Xms256m --J=-Xmx256m --classpath=%GEMFIRE%\locator-dependencies.jar --J=-Dgemfire.http-service-port=7071 --J=-Dgemfire.OSProcess.ENABLE_OUTPUT_REDIRECTION=true"

set START_SERVER1="start server --name=server3 --locators=%IP%[%LOCATOR_PORT%] --J=-Xms512m --J=-Xmx512m --classpath=%GEMFIRE%\server-dependencies.jar;%PROJECT_JARS% --cache-xml-file=config\cache.xml --properties-file=config\gemfire.properties --server-port=%SERVER1_PORT% --J=-Dgemfire.start-dev-rest-api=true --J=-Dgemfire.http-service-bind-address=%IP% --J=-Dgemfire.http-service-port=7076 --J=-Dgemfire.ALLOW_PERSISTENT_TRANSACTIONS=true --J=-Dgemfire.bind-address=%IP% --J=-Dgemfire.OSProcess.ENABLE_OUTPUT_REDIRECTION=true"

set START_SERVER2="start server --name=server4 --locators=%IP%[%LOCATOR_PORT%] --J=-Xms512m --J=-Xmx512m --classpath=%GEMFIRE%\server-dependencies.jar;%PROJECT_JARS% --cache-xml-file=config\cache.xml  --properties-file=config\gemfire.properties --server-port=%SERVER2_PORT% --J=-Dgemfire.ALLOW_PERSISTENT_TRANSACTIONS=true --J=-Dgemfire.bind-address=%IP% --J=-Dgemfire.OSProcess.ENABLE_OUTPUT_REDIRECTION=true"

gfsh -e %START_LOCATOR% -e %START_SERVER1%  -e %START_SERVER2% -e "list members" -e "list regions" -e "exit"
