#export CUR_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
export CUR_DIR=.

gfsh <<!

connect --locator=localhost[10334]

run --file=$CUR_DIR/dept-data
run --file=$CUR_DIR/emp-data

!

