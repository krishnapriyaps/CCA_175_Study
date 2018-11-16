from pyspark import SparkContext, SparkConf

conf=SparkConf().setAppName("read Text")
sc = SparkContext(conf=conf)

dataRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/sqoop/customers_10/part-m-00000")

for rec in dataRDD.collect():
	print(rec)

#Generate Sequence File

dataRDD.map(lambda x: tuple(x.split(",",1))).saveAsSequenceFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark/customers_10_Seq")
