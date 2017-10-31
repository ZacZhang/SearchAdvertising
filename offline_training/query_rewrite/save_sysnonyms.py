import os
import sys
import glob
import json
import libmc
from libmc import (MC_HASH_MD5, MC_POLL_TIMEOUT, MC_CONNECT_TIMEOUT, MC_RETRY_TIMEOUT)

def store_synonyms(synonyms_input_dir):
    client = libmc.Client(
    ["127.0.0.1:11219"],comp_threshold=0, noreply=False, prefix=None,hash_fn=MC_HASH_MD5, failover=False)
    
    client.config(MC_POLL_TIMEOUT, 100)  # 100ms
    client.config(MC_CONNECT_TIMEOUT, 300)  # 300ms
    client.config(MC_RETRY_TIMEOUT, 5)  # 5s

    path = synonyms_input_dir

    for filename in glob.glob(path):
        print "input data file:",filename
        with open(filename, 'r') as f:
            for line in f:
                entry = json.loads(line.strip())
                key = entry['word']
                fields = entry['synonyms']
                #print type(fields)

                val = str(key.encode('utf-8'))
                for i in range(0,len(fields)):
                	val = val + "_" + fields[i].encode('utf-8').strip('""')
            
                #save to memcached
                client.set(key,val)
                print "key=",key
                print "val=",val
                #print type(val)


if __name__ == "__main__":
    synonyms_input_dir = "/Users/zhangzhichao/IdeaProjects/SearchAdvertising/python_code/query_rewrite/synonyms_2.txt"
    #synonyms_input_dir = "/Users/zhangzhichao/Documents/text.txt"

    store_synonyms(synonyms_input_dir)






