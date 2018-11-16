from pyspark import SparkConf,SparkContext

conf = SparkConf().setAppName("Read Sequnece file")

sc = SparkContext(conf=conf)

dataRDD =  sc.sequenceFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark/customers_10_Seq/part-00000","org.apache.hadoop.io.IntWritable", "org.apache.hadoop.io.Text")
for rec in dataRDD.collect():
	print(rec)

