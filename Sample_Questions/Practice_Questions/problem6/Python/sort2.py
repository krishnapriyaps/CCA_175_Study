
from pyspark import SparkContext, SparkConf
from pyspark.sql import SQLContext
from  pyspark.sql.types import *

conf=SparkConf().setAppName("Sort")
sc=SparkContext(conf=conf)
sqlContext = SQLContext(sc)


#datatRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/p93_products/part*")
datatRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/p93_products_sub/part*")
#for rec in datatRDD.take(10):
#	print(rec)

schema = StructType([StructField("product_id",IntegerType()),StructField("product_category_id",IntegerType(),True),StructField("product_name",StringType(),True),StructField("product_description",StringType(),True),StructField("product_price",FloatType(),True),StructField("product_image",StringType(),True)])
dataFrameRdd = sqlContext.createDataFrame(datatRDD.map(lambda l : l.split("|")), schema)

print("DataFrame Int and String: ")
for rec in dataFrameRdd.take(15):
	print(rec)

print("DataFrame Int and String order by product_category_id " )
for rec in dataFrameRdd.orderBy("product_category_id").collect():
	print(rec)
print("DataFrame Int and String order by product_category_id desc")
for rec in dataFrameRdd.orderBy(dataFrameRdd.product_category_id.desc()).take(15):
	print(rec)

sqlContext.registerDataFrameAsTable(dataFrameRdd, "table2")
print("Temporary table Int and String order by product_category_id")
sortedRDD=sqlContext.sql("select * from table2 order by product_category_id ")
for rec in sortedRDD.take(15):
	print(rec)

print("Temporary table Int and String Desc order by product_category_id")
sortedDescRDD=sqlContext.sql("select * from table2 order by product_category_id desc")
for rec in sortedRDD.take(15):
	print(rec)




