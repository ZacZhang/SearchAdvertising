import sys
import json
from pyspark import SparkContext

def get_adid_terms(line):
	entry = json.loads(line.strip())
	ad_id = entry['adId']
	adid_terms = []

	for term in entry['keyWords']:
		val = str(ad_id) + "_" + term
		adid_terms.append(val)

	return adid_terms

def generate_json(items):
	result = {}
	result['adid_terms'] = items[0]
	result['count'] = items[1]
	return json.dumps(result)


if __name__ == "__main__":
	# ads.txt: /Users/zhangzhichao/IdeaProjects/SearchAdvertising/data/ads.txt
	adfile = sys.argv[1]
	sc = SparkContext(appName="TF_Features")

	data = sc.textFile(adfile).flatMap(lambda line: get_adid_terms(line)).map(lambda w: (w,1)).reduceByKey(lambda v1,v2: v1+v2).map(generate_json)
	data.saveAsTextFile("/Users/zhangzhichao/IdeaProjects/SearchAdvertising/data/log/TF")

	sc.stop()