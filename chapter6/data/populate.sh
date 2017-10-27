#export CUR_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

gfsh <<!

connect --locator=localhost[10334]

run --file=dept-data
run --file=emp-data

!

