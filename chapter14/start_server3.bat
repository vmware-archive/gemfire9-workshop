call ..\setenvironment.bat
gfsh start server --name=server3 --locators=%IP%[10334] --J=-Xms512m --J=-Xmx512m --classpath=..\..\projects\Domain\target\Domain-1.jar --cache-xml-file=config\cache.xml --properties-file=config\gemfire.properties --server-port=40406  --J=-Dgemfire.OSProcess.ENABLE_OUTPUT_REDIRECTION=true
