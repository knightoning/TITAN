#!/bin/bash

source /etc/profile

#获取本机agents的PID
pid=`ps -ef | grep "titan-agent" | grep -v "grep" | awk '{print $2}'`
echo $pid

#kill本机的所有agents节点
kill -9 $pid
echo "titan-agent进程已经全部kill"
