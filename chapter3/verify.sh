gfsh <<!
connect --locator=gemhost[10334]
list members;
list regions;
query --query="select * from /departments";
query --query="select * from /employees";
exit;
!

