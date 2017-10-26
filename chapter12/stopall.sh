# bring down servers in descending order as a discipline for advanced restarting

gfsh <<!

connect --locator=gemhost[10334]

stop server --name=server2

shutdown --include-locators=true
Y

exit;
!

