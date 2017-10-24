import os
import sys
import glob
import json
import libmc
from libmc import (MC_HASH_MD5, MC_POLL_TIMEOUT, MC_CONNECT_TIMEOUT, MC_RETRY_TIMEOUT)

def store_tf(feature_dir):
    client = libmc.Client(
    ["127.0.0.1:11220"],comp_threshold=0, noreply=False, prefix=None,hash_fn=MC_HASH_MD5, failover=False)
    client.config(MC_POLL_TIMEOUT, 100)  # 100ms
    client.config(MC_CONNECT_TIMEOUT, 300)  # 300ms
    client.config(MC_RETRY_TIMEOUT, 5)  # 5s
    path = feature_dir + "/part*"

    for filename in glob.glob(path):
        print "input data file:",filename
        with open(filename, 'r') as f:
            for line in f:
                entry = json.loads(line.strip())
                key = entry['adid_terms']
                val = str(entry['count'])
                
                # save to memcached
                client.set(key,val)
                print "key=",key
                print "val=",val

def store_df(feature_dir):
    client = libmc.Client(
    ["127.0.0.1:11221"],comp_threshold=0, noreply=False, prefix=None,hash_fn=MC_HASH_MD5, failover=False)
    client.config(MC_POLL_TIMEOUT, 100)  # 100ms
    client.config(MC_CONNECT_TIMEOUT, 300)  # 300ms
    client.config(MC_RETRY_TIMEOUT, 5)  # 5s
    path = feature_dir + "/part*"

    for filename in glob.glob(path):
        print "input data file:",filename
        with open(filename, 'r') as f:
            for line in f:
                entry = json.loads(line.strip())
                key = entry['doc_freq']
                val = str(entry['count'])
                
                # save to memcached 
                client.set(key,val)
                print "key=",key
                print "val=",val

if __name__ == "__main__":
    tf_input_dir = "/Users/zhangzhichao/IdeaProjects/SearchAdvertising/data/log/TF"
    df_input_dir = "/Users/zhangzhichao/IdeaProjects/SearchAdvertising/data/log/DF"

    store_tf(tf_input_dir)
    store_df(df_input_dir)
