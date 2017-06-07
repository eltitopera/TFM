FROM java:8 
MAINTAINER eltitopera

RUN apt-get update -y
WORKDIR "/opt/"
ADD https://github.com/elastic/logstash/archive/v5.4.0.tar.gz .
RUN tar xvf v5.4.0.tar.gz  && mv logstash-5.4.0 logstash 
RUN apt-get update -y
RUN apt-get install -y ruby-full
RUN apt-get -y install rake && apt-get update -y
WORKDIR "/opt/logstash"
RUN rake bootstrap
ADD log.conf .
RUN echo "/opt/logstash/bin/logstash -f /opt/logstash/log.conf" >> start.sh
RUN chmod 755 start.sh

CMD ["/bin/bash", "/opt/logstash/start.sh" ]

