FROM python
MAINTAINER eltitopera

RUN apt-get update -y && pip install rethinkdb && pip install redis && pip install elasticsearch && pip install kafka && pip install kafka-python && pip install tweepy && apt-get install nano -y
WORKDIR "/opt/"
COPY tweets.py .
COPY sentTweet.py .
COPY luislop.json .
COPY test.json .
COPY manual.py .


RUN echo "python tweets.py & " >> start.sh 
RUN echo "python sentTweet.py & " >> start.sh && chmod 755 start.sh


