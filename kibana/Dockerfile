FROM debian:latest
MAINTAINER jose.santas@gmail.com

COPY kibana.yml /opt/ 
 ## Create User
RUN apt-get update -y \
 && apt-get install wget -y \
 && groupadd -r kibana && useradd -r -g kibana kibana \
 ##Install kibana
 && cd /opt && wget https://download.elastic.co/kibana/kibana/kibana-4.6.3-linux-x86_64.tar.gz -O kibana-4.6.3-linux-x86_64.tar.gz \
 && tar zxvf kibana-4.6.3-linux-x86_64.tar.gz \
 && mv kibana-4.6.3-linux-x86_64 kibana 

 RUN mv /opt/kibana/config/kibana.yml /opt/kibana/config/kibana.yml.bk &&  mv /opt/kibana.yml /opt/kibana/config/ 

 RUN chown -R kibana:kibana /opt/kibana \
 ## Config plugins
 && cd /opt/kibana \
 && ./bin/kibana plugin --install sentinl -u https://github.com/sirensolutions/sentinl/releases/download/snapshot/sentinl-latest.tar.gz \
 && ./bin/kibana plugin --install kibrand -u https://github.com/elasticfence/kibrand/archive/0.4.5.zip \
 && ./bin/kibana plugin -i tagcloud -u https://github.com/stormpython/tagcloud/archive/master.zip \
 && ./bin/kibana plugin -i prelert_swimlane_vis -u https://github.com/prelert/kibana-swimlane-vis/archive/v0.1.0.tar.gz \
 && ./bin/kibana plugin --install elastic/timelion \
 && ./bin/kibana plugin --install elastic/sense \
 && ./bin/kibana plugin --install graph --url https://download.elasticsearch.org/elasticsearch/graph/graph-2.4.5.tar.gz \
 && chown -R kibana:kibana /opt/kibana

 RUN echo "/opt/kibana/bin/kibana >> kibana.log" >> start.sh && chmod 755 start.sh 

CMD ["/bin/bash","start.sh"]
