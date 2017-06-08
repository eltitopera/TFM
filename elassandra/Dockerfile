FROM debian:latest
MAINTAINER jose.santas@gmail.com

RUN \
 # Enable distribution testing repository
 echo "deb     http://mirror.steadfast.net/debian/ testing main contrib non-free" >> /etc/apt/sources.list.d/testing.list \
 && echo "deb     http://ftp.us.debian.org/debian/    testing main contrib non-free" >> /etc/apt/sources.list.d/testing.list \
 # Enable elassandra repository or download the debian package.
 && echo "deb http://packages.elassandra.io/deb/ ./" >> /etc/apt/sources.list.d/elassandra.list \
 # dependencies
 && apt-get update -y \
 && apt-get install wget -y \
 # Import Elassandra GPG public key
 && wget -O- -q http://packages.elassandra.io/pub/GPG-KEY-Elassandra > /tmp/GPG-KEY-Elassandra \
 && apt-key add  /tmp/GPG-KEY-Elassandra \
 # Install Cassandra Driver, and cqlsh
 && apt-get install python-pip python-cassandra wget curl libjemalloc1 zip sudo -y\
 && pip install --upgrade pip \
 && pip install --upgrade cassandra-driver \
 && pip install cqlsh \
 # Install Java
 && apt-get -y --force-yes install oracle-java8-jre \
 && update-alternatives --auto java \
 # Install JNA for Debian and Ubuntu
 && apt-get install libjna-java -y \
 # Update packages
 && apt-get update -y \
 ## Install Elassandra
 && cd /opt/ && wget https://github.com/strapdata/elassandra/releases/download/v2.4.2-12/elassandra-2.4.2.zip \
 && unzip -x /opt/elassandra-2.4.2.zip

ENV JAVA_HOME /usr/lib/jvm/oracle-java8-jre-amd64/
ENV CASSANDRA_HOME /opt/elassandra-2.4.2

COPY cassandra.yaml /opt/
#COPY tables.cql /opt/

RUN mv /opt/elassandra-2.4.2/conf/cassandra.yaml /opt/elassandra-2.4.2/conf/cassandra.yaml.bk &&  mv /opt/cassandra.yaml /opt/elassandra-2.4.2/conf/ 
RUN groupadd -r elassandra && useradd -r -g elassandra elassandra && chown -R elassandra:elassandra /opt/elassandra-2.4.2/
RUN echo "source $CASSANDRA_HOME/bin/aliases.sh; sudo -u elassandra bash -c '/opt/elassandra-2.4.2/bin/cassandra -e'; sleep 60; tail -f $CASSANDRA_HOME/logs/system.log" >> start.sh && chmod 755 start.sh && mv start.sh /opt/elassandra-2.4.2/

EXPOSE 9200/tcp 

# Exec on start
WORKDIR /opt/elassandra-2.4.2/
CMD ["/bin/bash","/opt/elassandra-2.4.2/start.sh"]
