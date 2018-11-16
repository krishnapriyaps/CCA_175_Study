#Now sort the products data sorted by product price per category, use productcategoryid colunm to group by category
from pyspark import SparkConf, SparkContext
from pyspark.sql import SQLContext

conf = SparkConf().setAppName("problem6 Atttempt 20")
sc = SparkContext(conf=conf)
sqlContext =  SQLContext(sc)
# Approach 1 : Using DF

rdd = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/p93_products_sub_2/pa*")
from pyspark.sql import Row
product = Row('product_id','product_category_id','product_name','product_description','product_price','product_image')
dataPrepBeforeDF = rdd.map(lambda l : l.split(",")).map(lambda a : product(int(a[0]),int(a[1]),a[2],a[3],a[4],a[5]))
productsDF = dataPrepBeforeDF.toDF()

## Data Frame Create Apporach 2
from pyspark.sql.types import *
columns = [ StructField("product_id",IntegerType(),True),StructField("product_category_id",IntegerType(),True),StructField("product_name",StringType(),True),StructField("product_description",StringType(),True), StructField("product_price",FloatType(),True), StructField("product_image",StringType(),True)]
dataPrepBeforeDF2 = rdd.map(lambda l : l.split(",")).map(lambda a : Row(int(a[0]),int(a[1]),a[2],a[3],a[4],a[5]))
productsDF2 = sqlContext.createDataFrame(dataPrepBeforeDF2, columns)

## Using collect by list
productsDF.groupBy("product_category_id").agg(collect_list("product_id"))
#NameError: name 'collect_list' is not defined


# Approach 2 : Using RDD
## Use groupByKey 
rdd = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/p93_products/pa*")
## Create a Map RDD for key : product_category_id ; Value : Tuple/Array (product_id,product_price )
pairedRDD2 = rdd.map(lambda l : l.split('~') ).map(lambda a : (a[1], (a[0],a[4])))
## Create a group by key
groupedRDD2 = pairedRDD2.groupByKey()
## Sort iterables returned after groupByKey - specifyin the sort key
sortedRDD2 = groupedRDD2.mapValues(lambda v : sorted(list(v), key = lambda l : l[1]))
##Save results
sortedRDD2.saveAsTextFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/sortedRDDproblem6py")

##Sample data
## (u'4', [(u'72', u'149.99'), (u'59', u'159.99'), (u'62', u'179.97'), (u'69', u'179.97'), (u'66', u'1799.99'), (u'65', u'199.99'), (u'63', u'209.99'), (u'64', u'21.99'), (u'52', u'249.97'), (u'57', u'28.0'), (u'53', u'29.99'), (u'67', u'29.99'), (u'49', u'299.98'), (u'58', u'299.99'), (u'61', u'299.99'), (u'68', u'309.99'), (u'71', u'349.98'), (u'55', u'39.99'), (u'50', u'59.98'), (u'51', u'69.99'), (u'70', u'79.99'), (u'54', u'99.0'), (u'56', u'99.95'), (u'60', u'999.99')])
##(u'8', [(u'155', u'134.99'), (u'150', u'149.99'), (u'148', u'199.99'), (u'147', u'21.99'), (u'165', u'21.99'), (u'151', u'24.97'), (u'167', u'25.99'), (u'146', u'28.0'), (u'149', u'29.99'), (u'158', u'29.99'), (u'153', u'299.99'), (u'160', u'31.97'), (u'168', u'32.0'), (u'156', u'34.99'), (u'166', u'34.99'), (u'159', u'39.99'), (u'162', u'399.99'), (u'161', u'44.99'), (u'157', u'45.0'), (u'164', u'45.0'), (u'145', u'59.98'), (u'154', u'69.99'), (u'163', u'69.99'), (u'152', u'99.98')])
##(u'57', [(u'1284', u'0.0'), (u'1293', u'109.99'), (u'1294', u'134.99'), (u'1281', u'159.99'), (u'1289', u'159.99'), (u'1297', u'174.99'), (u'1279', u'189.99'), (u'1276', u'29.99'), (u'1295', u'29.99'), (u'1283', u'34.99'), (u'1280', u'39.99'), (u'1286', u'44.99'), (u'1292', u'59.97'), (u'1287', u'59.99'), (u'1288', u'59.99'), (u'1291', u'70.0'), (u'1275', u'75.0'), (u'1296', u'9.99'), (u'1274', u'90.0'), (u'1277', u'90.0'), (u'1278', u'90.0'), (u'1282', u'90.0'), (u'1285', u'90.0'), (u'1290', u'99.99')])
