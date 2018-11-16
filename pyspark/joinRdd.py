from pyspark import SparkConf,SparkContext

conf = SparkConf().setAppName("Join_Rdd")
sc = SparkContext(conf=conf)

customerTableDataRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/sqoop/customers_10/part-m-00000")

customerDataAddRdd = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark/testData_customers_10.txt")

customerTableDataMapRDD =  customerTableDataRDD.map(lambda x : (int(x.split(",")[0]),x.split(",")[1]))
customerDataAddMapRdd =  customerDataAddRdd.map(lambda x : (int(x.split(",")[0]),x.split(",")[1]))

customerJoinedRdd = customerTableDataMapRDD.join(customerDataAddMapRdd)
for rec in customerJoinedRdd.collect():
	key = rec[0]
	valueConcat = ''
	for idx in range(len(rec[1])):
		valueConcat += str(rec[1][idx])+"*"
	valueConcat = valueConcat[:-1]
	print(str(key)+" <=>"+valueConcat)
