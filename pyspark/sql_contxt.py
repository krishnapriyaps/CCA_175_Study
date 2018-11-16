
## Works in python 3
## os.enviro['SPARK_CLASSPATH'] = "/usr/share/java/mysql-connector-java.jar"
## to add lib jars 
## spark-submit --jars "/usr/share/java/mysql-connector-java.jar" <python script file>

from pyspark import SparkConf,SparkContext
from pyspark.sql import SQLContext

conf = SparkConf().setAppName("Sql Query")
sc=SparkContext(conf=conf)

sqlContext =  SQLContext(sc)

jdbcURL = "jdbc:mysql://quickstart.cloudera:3306/retail_db?user=retail_dba&password=cloudera"

dataRDD = sqlContext.load(source="jdbc", url=jdbcURL,dbtable="customers")
## dataRDD is a data frame
## for rec in dataRDD.collect():
##	print(rec)

print(dataRDD.count())
