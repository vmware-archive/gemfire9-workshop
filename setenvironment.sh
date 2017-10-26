#!/bin/bash


# PUT THIS INTO YOUR ~/.bash_profile since gfsh will use the variables from there


export GEMFIRE_HOME=/usr/local/Cellar/gemfire/8.1.0/libexec
export GEMFIRE=$GEMFIRE_HOME

export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)

# Note: the above on OSX resolves to:
#export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_71.jdk/Contents/Home

export GF_JAVA=$JAVA_HOME/bin/java

# this gets the IP of the machine for some gemfire bind addresses
IP=$(ifconfig en0 | awk '/inet /{print substr($2,1)}' | tail -n1)
HOSTNAME=gemhost

if [[ ${PATH} != *$JAVA_HOME* ]]; then
export PATH=$PATH:$JAVA_HOME/bin
    echo
    echo "Including JAVA in PATH=$PATH"
    echo
fi

# append GEMFIRE to PATH if not already there
if [[ ${PATH} != *$GEMFIRE* ]]; then
	export PATH=$PATH:$GEMFIRE/bin
    echo
    echo "Including GEMFIRE in PATH=$PATH"
    echo
fi
