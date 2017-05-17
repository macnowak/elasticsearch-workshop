#!/bin/bash
set -e

#data nodes
for i in  1 2 3
do

/home/esuser/elasticsearch/bin/elasticsearch -d --node.name=${HOSTNAME}_data${i} --node.master=false --node.data=true --discovery.zen.ping.unicast.hosts=elastic --path.data=/home/esuser/data/di${i}

done

#master node
exec /home/esuser/elasticsearch/bin/elasticsearch --node.name=${HOSTNAME}_master --node.master=true --node.data=false --discovery.zen.ping.unicast.hosts=elastic
