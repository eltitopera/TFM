#!/usr/bin/env python
# encoding: utf-8
import rethinkdb as r

if __name__ == '__main__':
    r.connect('localhost', 28015).repl()
    r.db_create('tweets').run()
    r.db("tweets").table_create("tweet").run()
