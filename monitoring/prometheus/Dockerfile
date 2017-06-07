FROM debian:latest
MAINTAINER eltitopera


WORKDIR "/opt/"
ADD https://github.com/prometheus/prometheus/releases/download/v1.6.3/prometheus-1.6.3.linux-amd64.tar.gz . 
RUN tar xvf prometheus-1.6.3.linux-amd64.tar.gz && mv prometheus-1.6.3.linux-amd64 prometheus
ADD prometheus.yml /opt/prometheus/

WORKDIR "/opt/prometheus"
RUN echo "./prometheus"  >> start.sh
RUN chmod 755 start.sh

CMD ["/bin/bash", "start.sh"]
