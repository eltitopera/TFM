FROM python
MAINTAINER eltitopera

RUN apt-get update -y && pip install kafka && pip install kafka-python && apt-get install nano -y
WORKDIR "/opt/"
COPY episodes-es-json.txt .
COPY episodes2-es-jsonv1.txt .
COPY episodes2-es-jsonv2.txt .
COPY episodes2-es-jsonv3.txt .
COPY caracter.py .

#CMD [ "python27","twitter.py"]