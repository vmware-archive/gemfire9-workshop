gfsh <<!
connect --locator=localhost[10334]
list members;
list regions;
query --query="select * from /departments";
query --query="select * from /employees";
exit;
!

