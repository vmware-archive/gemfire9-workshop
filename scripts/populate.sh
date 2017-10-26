export CUR_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

gfsh <<!

connect

run --file=$CUR_DIR/dept-data
run --file=$CUR_DIR/emp-data

!

