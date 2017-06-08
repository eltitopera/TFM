#!/usr/bin/env python
# encoding: utf-8

import sys
import json
from kafka import KafkaProducer
import rethinkdb as r

def sent_tweet( id, text):
    r.connect('rethinkdb', 28015).repl()
    r.db('tweets').table('tweet').insert({'idtweet': id, 'text': text, 'type':'Tweet'}).run()


if __name__ == '__main__':
    producer = KafkaProducer(bootstrap_servers='kafka:9092')
    file = open("test.json","r")
    for line in file:
        print(line)
        t = json.loads(line.encode('utf-8'))
        sent_tweet(t["id_str"],t["text"])
        producer.send('tweet', line.encode('utf-8'))
