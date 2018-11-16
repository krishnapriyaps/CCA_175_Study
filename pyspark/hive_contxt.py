from pyspark import SparkConf, SparkContext
from pyspark.sql import HiveContext

conf = SparkConf().setAppName("Hive query")
sc = SparkContext(conf=conf)
sqlContext = HiveContext(sc)

dataRDD = sqlContext.sql("select * from customer_10")

for rec in dataRDD.collect():
	print(rec.customer_id,rec.customer_fname)

