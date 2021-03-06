## word count proram in python

from pyspark import SparkConf,SparkContext

conf =  SparkConf().setAppName("Word Count")

sc = SparkContext(conf=conf)

dataRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark/testdata.txt")

## split each row and flatten the arrays as rows
dataFlatMap = dataRDD.flatMap(lambda x: x.split(" "))

## Assign a vlue 1 for each word
dataMap =  dataFlatMap.map(lambda x: (x, 1))

## reduce the map by summing up all the 1s against each word which is a key hence reduceByKey
wordCount =  dataMap.reduceByKey(lambda x, y: x+y)

for rec in wordCount.collect():
	print(rec)




