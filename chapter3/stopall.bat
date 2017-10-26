call ..\setenvironment.bat
gfsh -e "connect --locator=%IP%[10334]" -e "stop server --name=server2" -e "shutdown --include-locators=true --time-out=60" -e "exit"
