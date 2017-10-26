set LOCATOR_PORT=10334
set SERVER2_PORT=40405
set PROJECT_JARS=..\..\projects\Domain\target\Domain-1.jar

call ..\setenvironment.bat

set START_LOCATOR="start locator --name=locator --properties-file=config\locator.properties --bind-address=%IP% --port=%LOCATOR_PORT% --J=-Xms256m --J=-Xmx256m --classpath=%GEMFIRE%\locator-dependencies.jar  --J=-Dgemfire.OSProcess.ENABLE_OUTPUT_REDIRECTION=true"

set START_SERVER1="start server --name=server1 --locators=%IP%[%LOCATOR_PORT%] --J=-Xms512m --J=-Xmx512m --classpath=%GEMFIRE%\server-dependencies.jar;%PROJECT_JARS% --cache-xml-file=config\cache.xml --properties-file=config\gemfire.properties --J=-Dgemfire.OSProcess.ENABLE_OUTPUT_REDIRECTION=true"

set START_SERVER2="start server --name=server2 --locators=%IP%[%LOCATOR_PORT%] --J=-Xms512m --J=-Xmx512m --classpath=%GEMFIRE%\server-dependencies.jar;%PROJECT_JARS% --cache-xml-file=config\cache.xml  --properties-file=config\gemfire.properties --server-port=%SERVER2_PORT% --J=-Dgemfire.OSProcess.ENABLE_OUTPUT_REDIRECTION=true"

gfsh -e %START_LOCATOR% -e %START_SERVER1%  -e %START_SERVER2% -e "list members" -e "list regions" -e "exit"
