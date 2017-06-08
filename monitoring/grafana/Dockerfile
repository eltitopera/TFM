FROM debian:latest
MAINTAINER eltitopera

WORKDIR "/opt/"
RUN apt-get update -y && apt-get -f install -y && apt-get install -y libfontconfig1 && apt-get install -y curl
ADD https://s3-us-west-2.amazonaws.com/grafana-releases/release/grafana_4.3.1_amd64.deb . 
RUN dpkg -i grafana_4.3.1_amd64.deb
ADD config.sh .
RUN chmod 755 config.sh && sleep 5 &&./config.sh
ADD start.sh .
RUN chmod 755 start.sh
CMD ["/bin/bash","/opt/start.sh"]
