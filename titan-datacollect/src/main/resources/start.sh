#!/bin/bash

source /etc/profile

java -server -Xms2048m -Xmx2048m -Xmn768m -XX:PermSize=128m -XX:MaxPermSize=128m -XX:+UseParallelOldGC -XX:ParallelGCThreads=3 -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:/usr/local/yunji/titan-datacollect/logs/gc.log -jar titan-datacollect.jar &
