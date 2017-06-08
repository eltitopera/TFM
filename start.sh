#!/bin/bash
#docker ps -a | tr -s ' ' | cut -d ' ' -f1-2

docker-compose up -d;

get_id_cont () {
i=0
#echo $cont_id $cont_name $3 $4
while [ $i -lt $total ]
do
#echo "e" ${cont_name[$i]}
	if [ ${cont_name[$i]} == $4 ]
	then
		id=${cont_id[$i]}
	fi

	i=$(expr $i + 1)
done

echo $id

}

declare -a cont_id
declare -a cont_name

cont_id=($(docker ps -a | tr -s ' ' | cut -d ' ' -f1 | sed '1d'))
cont_name=($(docker ps -a | tr -s ' ' | cut -d ' ' -f2 | sed '1d'))

total_cont=$(docker ps -a | tr -s ' ' | cut -d ' ' -f1-2 | wc -l)
total=$(expr $total_cont - 1)

kafka=$(get_id_cont $cont_id $cont_name $total "kafka")
elassandra=$(get_id_cont $cont_id $cont_name $total "elassandra")
kibana=$(get_id_cont $cont_id $cont_name $total "kibana")

#process_kafka=$(docker exec $kafka ps -aux | grep java | wc -l)
#process_elassandra=$(docker exec $elassandra ps -aux | grep java | wc -l)

#Waiting to load cassandra and elassandra
while [ $(docker exec $kafka ps -aux | grep java | wc -l) -lt 2 -o $(docker exec $elassandra ps -aux | grep java | wc -l) -lt 1 -o $(docker exec $kafka /opt/kafka/bin/kafka-topics.sh --zookeeper localhost:2181 --list | wc -l) -lt 4 ]
do
	echo "Waiting Kafka and Elassandra"
	sleep 30
done

echo "Working Kafka and Elassandra"
sleep 60

redis=$(get_id_cont $cont_id $cont_name $total "redis")
#process_redis=$(docker exec $redis ps -aux | grep 'src/redis-server 0.0.0.0:6379' | wc -l)

#Waiting to load redis
while [ $(docker exec $redis ps -aux | grep 'src/redis-server 0.0.0.0:6379' | wc -l) -lt 1 ]
do
        echo "Waiting Redis"
        sleep 30
done

echo "Working redis"
sleep 30
manager=$(get_id_cont $cont_id $cont_name $total "manager")
flink=$(get_id_cont $cont_id $cont_name $total "flink")

#process_manager=$(docker exec $manager ps -aux | grep 'java' | wc -l)
docker exec $manager /bin/bash /opt/start.sh &

#Waiting to load cassandra and elassandra
while [ $(docker exec $manager ps -aux | grep 'java' | wc -l) -lt 1 ]
do
        echo "Waiting Flink and Manager"
        sleep 30
done
echo "Working Flink and Manager"

docker exec $flink /bin/bash /opt/flink/start.sh &

while [ $(docker exec $flink ps -aux | grep 'java' | wc -l) -lt 4  ]
do
        echo "Waiting Flink"
        sleep 30
done

tweets=$(get_id_cont $cont_id $cont_name $total "tweets")
docker exec $tweets /bin/bash /opt/start.sh &
#process_tweets=$(docker exec $tweets ps -aux | grep 'python' | wc -l)

#Waiting for tweets
while [ $(docker exec $tweets ps -aux | grep 'python' | wc -l) -lt 2 ]
do
	echo "Waiting Tweets"
	sleep 30
done

echo "go go go....! "


cd monitoring/;
docker-compose up -d
