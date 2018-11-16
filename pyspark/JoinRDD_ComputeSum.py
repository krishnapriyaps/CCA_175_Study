from pyspark import SparkConf,SparkContext

conf = SparkConf().setAppName("joinRDD_ComputeSum.py")
sc = SparkContext(conf=conf)

ordersRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/sqoop/orders/part*")

orderItemsRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/sqoop/order_items/part*")

# order table [0] is order id
ordersMapRDD = ordersRDD.map(lambda x : (int(x.split(",")[0]), x.split(",",1)[1]))

# order items table [1] is order id
orderItemsMapRDD = orderItemsRDD.map(lambda x : (int(x.split(",")[1]), x.split(",",1)[1]))

#for rec in ordersMapRDD.take(5):
#	print(rec)

#for rec in orderItemsMapRDD.take(5):
#	print(rec)

# larger rdd joined with smaller
order_orderitem_join_rdd = orderItemsMapRDD.join(ordersMapRDD)
order_orderitem_join_map_rdd  =  order_orderitem_join_rdd.map(lambda x : (str(x[0])+"_"+x[1][1].split(",")[0],float(x[1][0].split(",")[3])))
order_orderitem_join_redbykey_rdd = order_orderitem_join_map_rdd.reduceByKey(lambda x, y : x+y)

#print(type(order_orderitem_join_rdd))
#rdd =  order_orderitem_join_rdd.parallelize(range(10))
#print(type(rdd))
# Find total order
for rec in order_orderitem_join_redbykey_rdd.take(10):
	print(rec)

print(order_orderitem_join_redbykey_rdd.count())


totalrevenue = order_orderitem_join_redbykey_rdd.reduce(lambda x, y :  x[1]+y[1])
print(totalrevenue)
