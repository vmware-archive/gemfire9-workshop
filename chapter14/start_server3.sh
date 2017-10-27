LOCATOR_PORT=10334
SERVER3_PORT=40407
PROJECT_JARS=../../projects/Domain/target/Domain-1.jar
LOCAL_IP=
IP=$(ifconfig | awk '/inet /{print substr($2,1)}' | tail -n1)

echo IP=$IP

. ../setenvironment.sh

gfsh <<!
connect --locator=localhost[10334]

start server --name=server3 --locators=$HOSTNAME[$LOCATOR_PORT] --J=-Xms512m --J=-Xmx512m --classpath=$GEMFIRE/lib/server-dependencies.jar:$PROJECT_JARS --cache-xml-file=config/cache.xml --properties-file=config/gemfire.properties --server-port=$SERVER3_PORT

exit
!
