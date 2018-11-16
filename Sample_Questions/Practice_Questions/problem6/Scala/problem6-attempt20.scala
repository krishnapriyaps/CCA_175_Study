//Now sort the products data sorted by product price per category, use productcategoryid colunm to group by category
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.types._

val conf = new SparkConf().setAppName("problem6 Atttempt 20")
val sc = new SparkContext(conf)
val sqlContext = new SQLContext(sc)


val rdd = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/p93_products_sub_2/pa*")

// Approach 1 : Using DF -  But to correct completely
// Data Frame Create Apporach 1
case class Person(product_id: Int, product_category_id: Int, product_name: String, product_description: String,product_price: Float,product_image: String)
val dataPrepBeforeDF = rdd.map(l => l.split(',')).map(a => Person(a(0).trim.toInt, a(1).trim.toInt,a(2),a(3), a(4).trim.toFloat,a(5)))
val productDF = dataPrepBeforeDF.toDF

// Data Frame Create Apporach 2
val structFields =  new Array[StructField](6)
structFields(0) = StructField("product_id",IntegerType,true)
structFields(1) = StructField("product_category_id",IntegerType,true)
structFields(2) = StructField("product_name",StringType,true)
structFields(3) = StructField("product_description",StringType,true)
structFields(4) = StructField("product_price",FloatType,true)
structFields(5) = StructField("product_image",StringType,true)
val schema =  new StructType(structFields)

// substep - Create RDD of Row
import org.apache.spark.sql.Row
val rowRDD = rdd.map(_.split(",")).map(a => Row(a(0).trim.toInt,a(1).trim.toInt,a(2),a(3),a(4).trim.toFloat,a(5)))
val productDF2 = sqlContext.createDataFrame(rowRDD,schema)


// Problem Statement Solution  Apprach 1
val solution1 = productDF.groupBy("product_category_id").sum("product_price").show

productDF.groupBy("product_category_id").agg(Map("product_price" -> "sum", "product_price" -> "avg")).orderBy(sum("product_price")).collect
orderBy("product_price")

import org.apache.spark.sql.functions._

// Using collect by list
productDF.groupBy("product_category_id").collect_List("product_price").orderBy(sum("product_price")).collect
//<console>:39: error: value collect_List is not a member of org.apache.spark.sql.GroupedData
//              productDF.groupBy("product_category_id").collect_List("product_price")


// Approach 2 : Using RDD
//Create paredRDD with key as - product_category_id 

// Use groupByKey 
val rdd = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/p93_products/pa*")
// Create a Map RDD for key : product_category_id ; Value : Tuple/Array (product_id,product_price )
val pairedRDD2 = rdd.map(l => (l.split("~")(1), (l.split("~")(0), l.split("~")(4))))
// Create a group by key
val groupedRDD2 = pairedRDD2.groupByKey()
// Sort iterables returned after groupBy Key
val sortedRDD2 = groupedRDD2.mapValues(v => v.toList.sortBy((_._2)))
//Save results
sortedRDD2.saveAsTextFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/sortedRDDproblem6")

/*
Sample
(4,List((72,149.99), (59,159.99), (62,179.97), (69,179.97), (66,1799.99), (65,199.99), (63,209.99), (64,21.99), (52,249.97), (57,28.0), (53,29.99), (67,29.99), (49,299.98), (58,299.99), (61,299.99), (68,309.99), (71,349.98), (55,39.99), (50,59.98), (51,69.99), (70,79.99), (54,99.0), (56,99.95), (60,999.99)))
(8,List((155,134.99), (150,149.99), (148,199.99), (147,21.99), (165,21.99), (151,24.97), (167,25.99), (146,28.0), (149,29.99), (158,29.99), (153,299.99), (160,31.97), (168,32.0), (156,34.99), (166,34.99), (159,39.99), (162,399.99), (161,44.99), (157,45.0), (164,45.0), (145,59.98), (154,69.99), (163,69.99), (152,99.98)))
(57,List((1284,0.0), (1293,109.99), (1294,134.99), (1281,159.99), (1289,159.99), (1297,174.99), (1279,189.99), (1276,29.99), (1295,29.99), (1283,34.99), (1280,39.99), (1286,44.99), (1292,59.97), (1287,59.99), (1288,59.99), (1291,70.0), (1275,75.0), (1296,9.99), (1274,90.0), (1277,90.0), (1278,90.0), (1282,90.0), (1285,90.0), (1290,99.99)))
*/

