from pyspark import SparkConf, SparkContext

conf =  SparkConf().setAppName("Creating Python RDD")

# Create RDD from Collection
collection1 =  range (1,50,2)
collection1RDD = sc.parallelize(collection1)
collection1RDD.collect()

# Create RDD from textFile
textfileRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/p93_products_sub/pa*")
textfileRDD.collect()

# Write RDD to text file
textfileRDD.saveAsTextFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/writerrddtotext.txt*")

# Write RDD to text file
codec = "org.apache.hadoop.io.compress.GzipCodec"
collection1RDD.saveAsTextFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/writerrddtotextCompress",codec)

# Create RDD of Key - Value and same as Sequence
textfileRDD.map(lambda l : l.split("|")).map(lambda a: (a[0], ",".join(map(str,a)))).collect()
textfileRDD.map(lambda l : (l.split("|")[0], l)).collect()
textfileRDD.map(lambda l : (l.split("|")[0], l)).saveAsSequenceFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/writerddtosequencefile")

# Write RDD to Sequence
sequeceFileRDD = sc.sequenceFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/writerddtosequencefile/pa*")


# Approach 1 : Using DF

rdd = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/p93_products_sub_2/pa*")
from pyspark.sql import Row
product = Row('product_id','product_category_id','product_name','product_description','product_price','product_image')
dataPrepBeforeDF = rdd.map(lambda l : l.split(",")).map(lambda a : product(int(a[0]),int(a[1]),a[2],a[3],a[4],a[5]))
productsDF = dataPrepBeforeDF.toDF()

from pyspark.sql import *
# write DF as JSON
productsDF.write.format("org.apache.spark.sql.json").save("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/writerdfasjson")

# write DF as Parquet
productsDF.write.format("org.apache.spark.sql.parquet").save("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/writerdfasparquet")

# write DF as ORC
productsDF.write.format("orc").save("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/writerdfasorc")

# write DF to external DBproducts_restore
productsDF.write.format("org.apache.spark.sql.jdbc").options(option("url","jdbc:mysql://quickstart:3306/retail_db?user=retail_dba&password=cloudera"),option("dbtable", "retail_db.products_restore")).save()
productsDF.write.mode("append").jdbc("jdbc:mysql://quickstart:3306/retail_db?user=retail_dba&password=cloudera", "retail_db.products_restore")



