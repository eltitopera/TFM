from kafka import KafkaProducer

producer = KafkaProducer(bootstrap_servers='kafka:9092')
files = ['episodes-es-json.txt','episodes2-es-jsonv1.txt','episodes2-es-jsonv2.txt','episodes2-es-jsonv3.txt']
cont = 0
for i in range(3):
    f = open(files[i], 'r')
    f.readline()
    for line in f:
        cont += 1
        producer.send('caracterToCass', line.encode('utf-8'))
        print(cont)
