set LOCATOR_PORT=10334
gfsh -e "connect --locator=%IP%[%LOCATOR_PORT%]" -e "run --file=.\dept-data" -e "run --file=.\emp-data"


