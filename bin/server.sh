#!/bin/bash

NAME=admin
JAVAPROJS=/home/pubsrv/javaprojs/
EXECUTEDIR=/home/pubsrv/javaprojs/opsbackend
JAVA_CMD=/usr/local/jdk8/bin/java
JAVA_PARAMS="-Dspring.profiles.active=prod-india -Dserver.port=9010 -Xms1g -Xmx1g  -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:+HeapDumpOnOutOfMemoryError -XX:+DisableExplicitGC -jar"
JAVA_JAR="$NAME".jar
DATETIME_NOW=`date '+%Y-%m-%d-%H:%M:%S'`

if [ ! -d "$EXECUTEDIR" ]; then
    echo "ERROR: $EXECUTEDIR is not a dir"
    exit
fi

if [ ! -d "$EXECUTEDIR"/runtime/ ]; then
    mkdir "$EXECUTEDIR"/runtime/
fi

if [ ! -f "$EXECUTEDIR"/"$JAVA_JAR" ]; then
    echo "ERROR: $EXECUTEDIR/$JAVA_JAR is not existed"
    exit
fi

cd "$EXECUTEDIR"
PID_FILE="$EXECUTEDIR"/runtime/server.pid

backup_logs(){
    if [ ! -d "$JAVAPROJS"/logs/"$NAME" ]; then
        mkdir -p "$JAVAPROJS"/logs/"$NAME"
    fi

    if [ -f "$EXECUTEDIR"/runtime/server.err ] ; then
        mv "$EXECUTEDIR"/runtime/server.err "$JAVAPROJS"/logs/"$NAME"/server-"$DATETIME_NOW".err
    fi

    if [ -f "$EXECUTEDIR"/runtime/server.log ] ; then
        mv "$EXECUTEDIR"/runtime/server.log "$JAVAPROJS"/logs/"$NAME"/server-"$DATETIME_NOW".log
    fi
}

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
    backup_logs
    echo "starting $NAME ..."
    #echo "$JAVA_CMD $JAVA_PARAMS $EXECUTEDIR/$JAVA_JAR"
    $JAVA_CMD $JAVA_PARAMS $EXECUTEDIR/$JAVA_JAR  2>"$EXECUTEDIR"/runtime/server.err >"$EXECUTEDIR"/runtime/server.log &
    PID=$!
    echo $PID > $PID_FILE
    sleep 1
    status
}

stop() {
    check_pid
    if [ $RETVAL -eq 0 ]; then
        echo "$NAME is running as $PID, stopping it..."
        # 因为服务器使用了多进程模式，需要给所有子进程发送信号
        # ps --ppid $PID -o pid h | xargs kill -9
        kill -9 $PID
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
exit $RETVAL