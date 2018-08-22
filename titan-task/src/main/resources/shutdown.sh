#!/bin/bash

source /etc/profile

#获取本机task的PID
pid=`ps -ef | grep "titan-task" | grep -v "grep" | awk '{print $2}'`
echo $pid

#kill本机的所有task节点
kill -9 $pid
echo "titan-task进程已经全部kill"
