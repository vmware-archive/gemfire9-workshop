@echo off

cd projects/Domain/target/classes
jar cvf ../Domain-1.jar io/*
cd ../../../
cd PeopleDomain/target/classes
jar cvf ../PeopleDomain-1.jar io/*
cd ../../../
cd ClientApp/target/classes
jar cvf ../ClientApp-1.jar io/*
cd ../../../
cd GemServer/target/classes
jar cvf ../GemServer-1.jar io/*
cd ../../../
cd ..


md chapter2\lib
md chapter2\locator
md chapter2\server1
md chapter2\server2
copy projects\Domain\target\Domain-1.jar chapter2\lib\
copy config\gemfire.properties chapter2\config\
copy config\cache.xml chapter2\config\

md chapter3\lib
md chapter3\locator
md chapter3\server1
md chapter3\server2
copy config\gemfire.properties chapter3\config\
copy config\cache.xml chapter3\config\

md chapter4\lib
md chapter4\locator
md chapter4\server1
md chapter4\server2
copy projects\Domain\target\Domain-1.jar chapter4\lib\
copy config\gemfire.properties chapter4\config\
copy chapter3\config\cache.xml chapter4\config\

md chapter5\lib
md chapter5\locator
md chapter5\server1
md chapter5\server2
copy projects\PeopleDomain\target\PeopleDomain-1.jar chapter5\lib
copy config\gemfire.properties chapter5\config\

md chapter6\lib
md chapter6\locator
md chapter6\server1
md chapter6\server2
copy projects\PeopleDomain\target\PeopleDomain-1.jar chapter6\lib
copy projects\ClientApp\target\ClientApp-1.jar chapter6\lib
copy config\gemfire.properties chapter6\config\
copy chapter4\endstate\config\cache.xml chapter6\config\cache.xml

md chapter7\lib
md chapter7\locator
md chapter7\server1
md chapter7\server2
copy config\gemfire.properties chapter7\config\
copy chapter4\endstate\config\cache.xml chapter7\config\cache.xml

md chapter8\lib
md chapter8\locator
md chapter8\server1
md chapter8\server2
copy chapter4\endstate\config\cache.xml chapter8\config\
copy -r chapter4\data chapter8\
copy config\gemfire.properties chapter8\config\
copy chapter6\config\query-client.xml chapter8\config\
copy projects\ClientApp\target\ClientApp-1.jar chapter8\lib
copy projects\Domain\target\Domain-1.jar chapter8\lib\

md chapter12\lib
md chapter12\locator
md chapter12\server1
md chapter12\server2
copy config\gemfire.properties chapter12\config\
copy chapter4\endstate\config\cache.xml chapter12\config\
copy chapter6\config\query-client.xml chapter12\config\
copy projects\ClientApp\target\ClientApp-1.jar chapter12\lib
copy projects\GemServer\target\GemServer-1.jar chapter12\lib\

md chapter13\lib
md chapter13\locator
md chapter13\server1
md chapter13\server2
copy config\gemfire.properties chapter13\config\
copy config\datalocations-cache-no-storage.xml chapter13\config\
copy projects\ClientApp\target\ClientApp-1.jar chapter13\lib

md chapter14\lib
md chapter14\locator
md chapter14\server1
md chapter14\server2
md chapter14\server3
copy config\gemfire.properties chapter14\config\
copy config\datalocations-cache-no-storage.xml chapter14\config\
copy projects\ClientApp\target\ClientApp-1.jar chapter14\lib
del /S/Q chapter14\server3\*

md chapter15\lib
md chapter15\locator
md chapter15\server1
md chapter15\server2
copy config\gemfire.properties chapter15\config\
copy config\cache-persistence.xml chapter15\config\

md chapter19\DataCenter1\lib
md chapter19\DataCenter1\locator1
md chapter19\DataCenter1\server1
md chapter19\DataCenter1\server2
md chapter19\DataCenter2\lib
md chapter19\DataCenter2\locator2
md chapter19\DataCenter2\server3
md chapter19\DataCenter2\server4

