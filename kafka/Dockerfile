FROM java:8 
MAINTAINER eltitopera

RUN apt-get update -y
WORKDIR "/opt/"
ADD http://apache.rediris.es/kafka/0.10.0.0/kafka_2.11-0.10.0.0.tgz .
RUN tar xvf kafka_2.11-0.10.0.0.tgz && mv kafka_2.11-0.10.0.0 kafka

ENV KAFKA_HOME /opt/kafka

WORKDIR "/opt/kafka/"
ADD luislop.json .
RUN echo "bin/zookeeper-server-start.sh -daemon config/zookeeper.properties; sleep 60;" >> start.sh
RUN echo "bin/kafka-server-start.sh -daemon config/server.properties; echo 'started'; sleep 10" >> start.sh 
RUN echo "/opt/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic tweets; Sleep 1;" >> start.sh
RUN echo "/opt/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic caracter; Sleep 1;" >> start.sh
RUN echo "/opt/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic wiki; Sleep 1;" >> start.sh 
RUN echo "/opt/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic Trigger_Tweet; Sleep 1;" >> start.sh
RUN echo "tail -f /opt/kafka/logs/kafkaServer.out" >> start.sh && chmod 755 start.sh
EXPOSE 2181 9092

CMD ["/bin/bash","/opt/kafka/start.sh" ]


