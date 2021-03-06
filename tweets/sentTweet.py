#!/usr/bin/env python
# encoding: utf-8
import tweepy
import sys
import rethinkdb as r
from kafka import KafkaConsumer
import json


#Twitter API credentials
CONSUMER_KEY = <CONSUMER_KEY>
CONSUMER_SECRET = <CONSUMER_SECRET>
ACCESS_KEY = <ACCESS_KEY>
ACCESS_SECRET = <ACCESS_SECRET>

size_tweet = 140

if __name__ == '__main__':
    auth = tweepy.OAuthHandler(CONSUMER_KEY, CONSUMER_SECRET)
    auth.set_access_token(ACCESS_KEY, ACCESS_SECRET)
    api = tweepy.API(auth)

    while True:
        consumer = KafkaConsumer(bootstrap_servers='kafka:9092')
        consumer.subscribe(['sentTweet'])
        for text in consumer:
            text=json.loads(text[6].decode('utf-8'))
            id_str=text["id_str"]
            text=text["text"]
            
            r.connect('rethinkdb', 28015).repl()
            r.db('tweets').table('tweet').insert({'idtweet': id_str,'text': text, 'type':'Created'}).run()
            try:
                tweets = [text[i:i+size_tweet] for i in range(0, len(text), size_tweet)]
                for tweet in tweets:
                    #Update tweet in account
                    api.update_status(tweet)
            except:
                print("Error to update new tweet")
