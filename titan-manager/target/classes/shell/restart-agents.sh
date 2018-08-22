#!/bin/bash

#每台机器启动的agent节点数量
AGENT_NUM=2

#所有agent机器地址
AGENT_ADDRESS=('192.168.1.1'
'192.168.1.2'
)

#停止所有机器上的agents节点
for address in ${AGENT_ADDRESS[*]}
do
	nohup ssh root@$address "cd /usr/local/yunji/titan-agent*; sh shutdown.sh &" >/dev/null 2>log &
done
echo '所有机器上的agent节点已经全部停止'

#等待所有机器上的agent节点全部停止后再重启
sleep 5

#重新启动所有机器上的agent节点
for i in `seq 1 $AGENT_NUM`
do
        for address in ${AGENT_ADDRESS[*]}
        do
        	nohup ssh root@$address "cd /usr/local/yunji/titan-agent*; sh start.sh &" >/dev/null 2>log &
	done
done
echo '所有机器上的agent节点已经全部重启'
