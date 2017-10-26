# bring down servers in descending order as a discipline for advanced restarting

gfsh <<!

connect --locator=gemhost[10334]

stop server --name=server2

shutdown
Y
stop locator --name=locator1

exit;
!

