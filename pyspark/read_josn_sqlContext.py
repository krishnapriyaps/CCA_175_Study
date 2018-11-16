## read Json
from pyspark import SparkConf,SparkContext
from pyspark.sql import SQLContext

conf = SparkConf().setAppName("Json read")
sc=SparkContext(conf=conf)

sqlContext =  SQLContext(sc)

customerJsonRDD = sqlContext.jsonFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark/customers_10_Json/part-00000")

customerJsonRDD.registerTempTable("customers_10_Json")

customerDataRDD = sqlContext.sql("select * from customers_10_Json")

for rec in customerDataRDD.collect():
	print(rec)
