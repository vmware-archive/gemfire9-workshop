call ..\setenvironment.bat
gfsh -e "connect --locator=%IP%[10334]" -e "shutdown --include-locators=true" -e "exit"