#!/bin/bash

SERVER_PORT=9010
JVM_PARAMS="-Xms2048m -Xmx2048m"
JVM_PARAMS="$JVM_PARAMS -Xss2m -XX:NewRatio=2 -XX:SurvivorRatio=8"
JVM_PARAMS="$JVM_PARAMS -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:ParallelGCThreads=8 -XX:MaxGCPauseMillis=50 -XX:CMSInitiatingOccupancyFraction=75"
#JVM_PARAMS="$JVM_PARAMS -XX:+DisableExplicitGC"
JVM_PARAMS="$JVM_PARAMS -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
JVM_PARAMS="$JVM_PARAMS -XX:MaxDirectMemorySize=50M"
JVM_PARAMS="$JVM_PARAMS -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -Xloggc:logs/gc.log -verbose:gc"
JVM_PARAMS="$JVM_PARAMS -XX:+HeapDumpOnOutOfMemoryError"
#JVM_PARAMS="$JVM_PARAMS -XX:+PrintConcurrentLocks -XX:PrintHeapAtGC"
JVM_PARAMS="$JVM_PARAMS -XX:HeapDumpPath=logs"
JVM_PARAMS="$JVM_PARAMS -XX:OnError=bin/error.sh>>logs/jvm_error.log"
JVM_PARAMS="$JVM_PARAMS -XX:OnOutOfMemoryError=bin/memerror.sh>>logs/jvm_oom.log"
JVM_PARAMS="$JVM_PARAMS -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCApplicationConcurrentTime -XX:+PrintTenuringDistribution"
JVM_PARAMS="$JVM_PARAMS -XX:-TraceClassLoading -XX:-TraceClassLoadingPreorder"
JVM_PARAMS="$JVM_PARAMS -XX:-TraceClassUnloading"
JVM_PARAMS="$JVM_PARAMS -XX:-TraceClassResolution"
JVM_PARAMS="$JVM_PARAMS -XX:-PrintCommandLineFlags -XX:-PrintCompilation"
JVM_PARAMS="$JVM_PARAMS -XX:-PrintClassHistogram"
JVM_PARAMS="$JVM_PARAMS -XX:-CITime"

