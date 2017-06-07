FROM ubuntu:latest
MAINTAINER eltitopera

RUN apt-get update -y && apt-get install build-essential -y && apt-get install tcl8.5 -y && apt-get install make
WORKDIR "/opt/"
ADD http://download.redis.io/redis-stable.tar.gz . 
RUN tar xvf redis-stable.tar.gz && mv /opt/redis-stable redis
WORKDIR "/opt/redis"
RUN make 

RUN mv /opt/redis/redis.conf  /opt/redis/redis.conf.bk
#in redis.conf config bind 0.0.0.0
COPY redis.conf /opt/redis/

RUN echo "src/redis-server redis.conf --daemonize yes; tail -f /dev/null" > start.sh
RUN chmod 755 start.sh

EXPOSE 6379

CMD ["/bin/bash","start.sh"]
