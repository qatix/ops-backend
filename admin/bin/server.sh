#!/bin/bash

env_path=$(dirname $0)/../../env.sh
if [ -f $env_path ]; then
    source $env_path
fi

NAME=admin
JAVA_CMD=`which java`
JAVA_PARAMS="-Dspring.profiles.active=$SERVER_ENV -Dserver.port=$SERVER_PORT $JVM_PARAMS -jar"
JAVA_IAST=''
JAVA_JAR="$NAME".jar

case "`uname`" in
    Linux)
        bin_abs_path=$(readlink -f $(dirname $0))
        ;;
    *)
        bin_abs_path=`cd $(dirname $0); pwd`
        ;;
esac
base=${bin_abs_path}/..

cd $base

if [ ! -d "logs" ]; then
  mkdir logs
fi

PID_FILE=/home/service/var/"${NAME}"_server.pid

check_running() {
    PID=0
    RETVAL=0
    check_pid
    if [ $RETVAL -eq 0 ]; then
        echo "$NAME is running as $PID, we'll do nothing"
        exit
    fi
}

check_pid() {
    RETVAL=1
    if [ -f $PID_FILE ]; then
        PID=`cat $PID_FILE`
        # ls /proc/$PID &> /dev/null
        ps -p $PID -o command= | grep java | grep $NAME
        if [ $? -eq 0 ]; then
            RETVAL=0
        fi
    fi
}

start() {
    check_running
    echo "starting $NAME ..."
    $JAVA_CMD $JAVA_IAST $JAVA_PARAMS $JAVA_JAR  2> logs/error.log > logs/server.log  &
    PID=$!
    echo $PID > $PID_FILE
    sleep 1
    status
}

stop() {
    check_pid
    if [ $RETVAL -eq 0 ]; then
        echo "$NAME is running as $PID, stopping it..."
        kill $PID
    else
        echo "$NAME is not running, do nothing"
    fi
    while true; do
        check_pid
        if [ $RETVAL -eq 0 ]; then
            echo "$NAME is running, waiting it's exit..."
            sleep 1
        else
            echo "$NAME is stopped safely, you can restart it now"
            break
        fi
    done
    if [ -f $PID_FILE ]; then
        rm $PID_FILE
    fi
}

status() {
    check_pid
    if [ $RETVAL -eq 0 ]; then
        echo "$NAME is running as $PID ..."
    else
        echo "$NAME is not running"
    fi
}

RETVAL=0
case "$1" in
    start)
        start $@
        ;;
    stop)
        stop
        ;;
    restart)
        stop
        start $@
        ;;
    status)
        status
        ;;
    *)
        echo "Usage: $0 {start|stop|restart|status}"
        RETVAL=1
esac

