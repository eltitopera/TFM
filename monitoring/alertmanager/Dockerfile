FROM debian:latest
MAINTAINER eltitopera


WORKDIR "/opt/"
ADD https://github.com/prometheus/alertmanager/releases/download/v0.7.0-rc.0/alertmanager-0.7.0-rc.0.linux-amd64.tar.gz .
RUN tar xvf alertmanager-0.7.0-rc.0.linux-amd64.tar.gz && mv alertmanager-0.7.0-rc.0.linux-amd64 alertmanager

