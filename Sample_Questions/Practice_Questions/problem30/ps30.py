#orderid , order_date , order_customer_id, order_status
ordersRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/sqoop/orders")
# order_item_id :int , order_item_order_id :int , :int, order_item_quantity :int ,order_item_subtotal :float, order_item_product_price :float
oiRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/sqoop/order_items")

#2. Join these data using orderid in Spark and Python
#3. Calculate total revenue perday and per order
#4. Calculate total and average revenue for each date. - combineByKey

ordersPRDD = ordersRDD.map( lambda l : (l.split(',')[0], l))
oiPRDD = oiRDD.map( lambda l : (l.split(',')[1],l))

oijoinRDD = ordersRDD.join(oiRDD)

>>> oijoinRDD.take(1)
[(u'1', (u',', u','))]
>>> oijoinRDD.map(lambda l: l(0)+l(1)(0)+l(1)(1)).take(1)
18/02/11 07:05:42 WARN scheduler.TaskSetManager: Lost task 0.0 in stage 8.0 (TID 13, 10.0.2.4, executor 2): org.apache.spark.api.python.PythonException: Traceback (most recent call last):
  File "/usr/lib/spark/python/pyspark/worker.py", line 111, in main
    process()
  File "/usr/lib/spark/python/pyspark/worker.py", line 106, in process
    serializer.dump_stream(func(split_index, iterator), outfile)
  File "/usr/lib/spark/python/pyspark/serializers.py", line 263, in dump_stream
    vs = list(itertools.islice(iterator, batch))
  File "/usr/lib/spark/python/pyspark/rdd.py", line 1293, in takeUpToNumLeft
    yield next(iterator)
  File "<stdin>", line 1, in <lambda>
TypeError: 'tuple' object is not callable


