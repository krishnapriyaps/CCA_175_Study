

// Scala 
/// *** POINT LEARNED :  need to create data frame with sql context #Apporach 2 (not wiht toDF #Approach 1) or else registerTemp Table not recognized to write data directly to hive table
/
/-- Q1 And  Q2
import org.apache.spark.sql._
import org.apache.spark.sql.hive._

val dataRDD =  sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/testdata/product.csv")
case class Person  (productID : Int,productCode:String,name:String,quantity:Int,price: Float)
val personRDD  = dataRDD.map(_.split(',')).map(a => Person(a(0).trim.toInt,a(1),a(2),a(3).trim.toInt,a(4).trim.toFloat))

val sql =  new org.apache.spark.sql.hive.HiveContext(sc)

// Approach 1 : Create DF with DF; Create actual table; write DF to temp table 
//Create Hive table o ORC format
val personDF = personRDD.toDF
personDF.write.format("orc").saveAsTable("family.person_ps14")

// Approach 2
val personDF2= sql.createDataFrame(personRDD)
personDF2.registerTempTable("person_csv") 
sql.sql("create table family.person_ps14_a2 STORED AS ORC AS select * from person_csv")

//-- Q3
sql.sql("create table family.person_ps14_parq STORED AS parquet AS select * from family.person_ps14_a2")



