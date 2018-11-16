//orderid , order_date , order_customer_id, order_status
val ordersRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/sqoop/orders/*")
// order_item_id :int , order_item_order_id :int , :int, order_item_quantity :int ,order_item_subtotal :float, order_item_product_price :float
val oiRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/sqoop/order_items/*")

//2. Join these data using orderid in Spark and Python
//3. Calculate total revenue perday and per order
//4. Calculate total and average revenue for each date. - combineByKey

val ordersPRDD = ordersRDD.map(_.split(',')).map( a => (a(0).trim.toInt, a(1)))
val oiPRDD = oiRDD.map(_.split(',')).map( a => (a(1).trim.toInt,a(4)))

// ordersPRDD and oiPRDD are not PairedRDD but MapPartitionRDD 
val oijoinRDD = ordersPRDD.join(oiPRDD)

// Revenue Perday per order: Created RDD with order1d,Date as key and revenue as float
val dateorderRDD = oijoinRDD.map(row => (row._1+","+row._2._1, row._2._2.trim.toDouble))

//CombineByKey

// Approach 1.................................
//create value initial tuple of (count , revenue)
val initializerFunc = (revenue: Double) => (1, revenue) 
type collection = (Int,  Double )

// Combine count and revenue within partition
val combineFunctionwithinPartition = ( collection1 : collection ,revenue2: Double) =>  (collection1._1 + 1,collection1._2+ revenue2)

//Combine output of partitions
val combineOutputOfPartitions = (collection1 : collection ,collection2: collection) => (collection1._1 + collection2._1, collection1._2 + collection2._2 )


//Total revenue
val revenuePerOrderPerDayRDD = dateorderRDD.combineByKey(initializerFunc, combineFunctionwithinPartition, combineOutputOfPartitions)
revenuePerOrderPerDayRDD take(1)
//revenuePerOrderPerDayRDD: org.apache.spark.rdd.RDD[(String, (Int, Double))] = ShuffledRDD[18] at combineByKey at <console>:47

//Average per key (orderif,Date)
type row = (String , (Int, Double))
val average = revenuePerOrderPerDayRDD2.map(l => (l._1, (l._2._2/l._2._1)))


// Approach 2.................................
//create value initial tuple of (count , revenue)
val initializerFunc2 = (revenue: Double) => (revenue, 1) 

type collection2 = (Double , Int )
// Combine count and revenue within partition
val combineFunctionwithinPartition2 = ( collection1 : collection2 ,revenue2: Double) =>  (collection1._1 + revenue2, collection1._2+ 1)
//Combine output of partitions
val combineOutputOfPartitions2 = (collection1 : collection2 ,collection2: collection2) => (collection1._1 + collection2._1, collection1._2 + collection2._2 )
//CombineByKey
//Total revenue
val revenuePerOrderPerDayRDD2 = dateorderRDD.combineByKey(initializerFunc2, combineFunctionwithinPartition2, combineOutputOfPartitions2)

val average = revenuePerOrderPerDayRDD2.map(l => (l._1, (l._2._1/l._2._2)))


// Sum by aggregate by key

val initialValue = 0.0
val aggregateWithinPartition = (revenue1 : Double, revenue2 : Double) => revenue1+ revenue2


val revenuePerOrderPerDayRDD2 = dateorderRDD.aggregateByKey(initialValue)(initializerFunc2, combineFunctionwithinPartition2, combineOutputOfPartitions2)

val totalRevenuePerDay = dateorderRDD.groupByKey().aggregate((revenue1 : Double, revenue2 : Double) => revenue1+ revenue2)

