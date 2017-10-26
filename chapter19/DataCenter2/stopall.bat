
call ..\setenvironment.bat
gfsh -e "connect --locator=%IP%[10335]" -e "shutdown --include-locators=true" -e "exit"