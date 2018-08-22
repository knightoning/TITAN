#!/bin/bash

source /etc/profile

#获取本机datacollect的PID
pid=`ps -ef | grep "titan-datacollect" | grep -v "grep" | awk '{print $2}'`
echo $pid

#kill本机的所有datacollect节点
kill -9 $pid
echo "titan-datacollect进程已经全部kill"
