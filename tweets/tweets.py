#!/usr/bin/env python
# encoding: utf-8

from tweepy.streaming import StreamListener
from elasticsearch import Elasticsearch
from kafka import KafkaProducer
import tweepy
from tweepy import OAuthHandler
from tweepy import Stream
from time import sleep
import time
import sys
import json
import datetime
import redis
import rethinkdb as r


#Twitter API credentials
<<<<<<< HEAD
CONSUMER_KEY = <CONSUMER_KEY>
CONSUMER_SECRET = <CONSUMER_SECRET>
ACCESS_KEY = <ACCESS_KEY>
ACCESS_SECRET = <ACCESS_SECRET>

class StdOutListener(StreamListener):
    def new_tweet(self, id):
        r = redis.StrictRedis(host='redis', port=6379)
        r.set(id, 'working')

    def end_tweet(self, id):
        r = redis.StrictRedis(host='redis', port=6379)
        r.set(id, 'end')

    def sent_tweet(self, id, text):
        r.connect('rethinkdb', 28015).repl()
        r.db('tweets').table('tweet').insert({'idtweet': id, 'text': text, 'type':'Tweet'}).run()


    def on_data(self, data):
        t = json.loads(data)
        self.new_tweet(t['id_str'])
        self.sent_tweet(t['id_str'],t['text'])
        producer.send('tweet', t)
        tweets = []
        sleep(2)

        timestamp_last_tweet = self.get_last_date_user(t['user']['screen_name'])
        self.get_all_tweets(t['user']['screen_name'], tweets, 0, timestamp_last_tweet, False)

        for i in range(len(tweets)):
            #print(json.dumps(tweets[i]._json))
            if tweets[i].lang == "es":
                producer.send('tweets', tweets[i]._json)
        self.end_tweet(t['id_str'])

        return True

    def on_error(self, status):
        print(status)

    def get_all_tweets(self, screen_name, alltweets=[], max_id=0, timestamp_last_tweet=0, mod=False):

        auth = tweepy.OAuthHandler(CONSUMER_KEY, CONSUMER_SECRET)
        auth.set_access_token(ACCESS_KEY, ACCESS_SECRET)
        api = tweepy.API(auth)
    
        #make initial request for most recent tweets (200 is the maximum allowed count)
        if max_id is 0:
            new_tweets = api.user_timeline(screen_name=screen_name, count=200)
    
        else:
            # new new_tweets
            new_tweets = api.user_timeline(screen_name=screen_name, count= 200, max_id=max_id)
            
        
    
        if len(new_tweets) > 0:
            #save most recent tweets
            if timestamp_last_tweet != "no limit":
                for i in range(len(new_tweets)):
                    if time.mktime(new_tweets[i].created_at.timetuple()) >  float(timestamp_last_tweet):
                        alltweets.append(new_tweets[i])
                    else:
                        mod = True
            else:
                alltweets.extend(new_tweets)

            sleep(2)
            oldest = alltweets[-1].id - 1
    
            if not mod :
                return self.get_all_tweets(screen_name=screen_name, alltweets=alltweets, max_id=oldest, timestamp_last_tweet=timestamp_last_tweet, mod=mod)
        return alltweets

    def get_last_date_user(self, screen_name):
        last_tweet_date = 0;
        es = Elasticsearch(['elassandra:9200'])
        res = es.search(index="twitter", doc_type="tweet", body={ "from":0,"size":1,"query":{"bool":{"must":{"match":{"user_screen_name":{"query":" \"" + screen_name + "\"","type":"phrase"}}}}},"_source":{"includes":["created_at","text"],"excludes":[]},"sort":[{"created_at":{"order":"desc"}}]})
        for hit in res['hits']['hits']:
            print(hit["_source"]["created_at"])
            last_tweet_date = hit["_source"]["created_at"]

        if last_tweet_date == 0:
            return "no limit"
        return last_tweet_date

if __name__ == '__main__':
    while True:
        try:
            l = StdOutListener()
            auth = OAuthHandler(CONSUMER_KEY, CONSUMER_SECRET)
            auth.set_access_token(ACCESS_KEY, ACCESS_SECRET)
            producer = KafkaProducer(bootstrap_servers='kafka:9092', value_serializer=lambda v: json.dumps(v).encode('utf-8'))

            stream = Stream(auth, l)
            stream.filter(languages=["es"], track=['#twipren'])
        except Exception:
            print('Error al recuperar el tweet')

