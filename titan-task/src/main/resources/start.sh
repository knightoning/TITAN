#!/bin/bash

source /etc/profile

java -server -Xms4g -Xmx4g -Xmn1g -Xss256k -XX:PermSize=128m -XX:MaxPermSize=128m -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=80 -XX:ParallelGCThreads=3 -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:/usr/local/yunji/titan-task/logs/gc.log -jar -Dlog_dir=/usr/local/yunji/logs/ titan-task.jar &

