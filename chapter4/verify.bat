
call ..\setenvironment.bat

gfsh -e "connect --locator=%IP%[10334]" -e "list members" -e "list regions"