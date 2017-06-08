FROM java:8
MAINTAINER eltitopera
WORKDIR "/opt/"
RUN mkdir NLP
ADD manager.jar .
ADD NLP NLP
RUN echo "java -cp manager.jar Manager.ServiceManager 5 >> logs/manager.log" >> start.sh && chmod 755 start.sh
#CMD ["/bin/bash","start.sh"]
