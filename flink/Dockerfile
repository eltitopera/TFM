FROM java:8 
MAINTAINER eltitopera

RUN apt-get update -y 
WORKDIR "/opt/"
ADD http://apache.rediris.es/flink/flink-1.3.0/flink-1.3.0-bin-hadoop27-scala_2.10.tgz .
RUN tar xvf flink-1.3.0-bin-hadoop27-scala_2.10.tgz && mv flink-1.3.0 flink
RUN mv /opt/flink/conf/flink-conf.yaml /opt/flink/conf/flink-conf.yaml.bk
ADD flink-conf.yaml /opt/flink/conf/

WORKDIR "/opt/flink/"
ADD ToElassandra.jar .
RUN echo "bin/start-local.sh; tail -f /dev/null" > up.sh
RUN chmod 755 up.sh
RUN echo "bin/flink run -p 1 -c SaveToElassandra.saveTweets ToElassandra.jar & " >> start.sh
RUN echo "bin/flink run -p 1 -c SaveToElassandra.saveWiki ToElassandra.jar & " >> start.sh
RUN echo "bin/flink run -p 1 -c SaveToElassandra.saveCaracter ToElassandra.jar & "  >> start.sh
RUN chmod 755 start.sh
EXPOSE 8081 6123

CMD [ "/bin/bash","up.sh" ]
