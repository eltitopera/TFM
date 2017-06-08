FROM python
MAINTAINER eltitopera

RUN apt-get update -y && pip install kafka && pip install kafka-python && apt-get install nano -y
WORKDIR "/opt/"
COPY wiki.py .
COPY long_abstracts_es.tql .

#CMD [ "python27","twitter.py"] 