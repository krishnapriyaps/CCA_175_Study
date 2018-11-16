## Python script to find distinct of values under a feild in csv file

from pyspark import SparkConf,SparkContext

conf =  SparkConf().setAppName("Distinct Type");

sc = SparkContext(conf=conf)

dataRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark/testData_customers_10.csv")

dataRDD2 = dataRDD.map(lambda line : line.split(",")[2]).distinct()

for rec in dataRDD2.collect():
	print(rec)

print (".........COUNT...........")
print (dataRDD2.count())



#dataRDD3 = dataRDD2.map(lambda row: row[2])

#for rec in dataRDD3.collect():
#        print(rec)



