FROM ubuntu
MAINTAINER eltitopera

RUN apt-get update -y && apt-get install -y wget && apt-get install -y python
RUN /bin/bash -c "source /etc/lsb-release" && echo "deb http://download.rethinkdb.com/apt xenial main" | tee /etc/apt/sources.list.d/rethinkdb.list
RUN wget -qO- https://download.rethinkdb.com/apt/pubkey.gpg | apt-key add - && apt-get update -y && apt-get install -y rethinkdb
RUN cp /etc/rethinkdb/default.conf.sample /etc/rethinkdb/instances.d/instance1.conf
RUN apt-get install -y python-setuptools python-dev build-essential && easy_install pip && pip install rethinkdb
ADD config.py . 
RUN chmod 755 config.py
RUN echo "rethinkdb --bind all --daemon ; sleep 10 ; python config.py ; tail -f /dev/null " >> start.sh && chmod 755 start.sh

EXPOSE 8080 29015

CMD ["/bin/bash","start.sh"]
