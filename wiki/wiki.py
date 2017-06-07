from kafka import KafkaProducer

producer = KafkaProducer(bootstrap_servers='kafka:9092')
f = open('long_abstracts_es.tql', 'r')
f.readline()
for line in f:
     #word
     inicio = line.find('resource/')+9;
     fin = line.find(">");
     #description
     inicio_desc = line.find('"')
     fin_desc = line.find('"', inicio_desc+1)
     #sent kafka
     text = '{"word":"'+ line[inicio:fin] +'", "desc":"'+ line[inicio_desc+1:fin_desc-1] +'"}'
     producer.send('wikiToCass', text.encode('utf-8'))
     
f.closed()