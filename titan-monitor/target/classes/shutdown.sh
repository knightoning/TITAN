#!/bin/bash

source /etc/profile

#获取本机monitor的PID
pid=`ps -ef | grep "titan-monitor" | grep -v "grep" | awk '{print $2}'`
echo $pid

#kill本机的所有monitor节点
kill -9 $pid
echo "titan-monitor进程已经全部kill"
