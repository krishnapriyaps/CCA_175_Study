

// Scala 
/// *** POINT LEARNED :  need to create data frame with sql context #Apporach 2 (not wiht toDF #Approach 1) or else registerTemp Table not recognized to write data directly to hive table
/
/-- Q1 And  Q2
import org.apache.spark.sql._
import org.apache.spark.sql.hive._

val sql =  new org.apache.spark.sql.hive.HiveContext(sc))

//-- Q1
val results = sql.sql("select * from family.person_ps14 where productcode is null and productcode == '' and length(productcode) == 0")
results.show()

//-- Q2
val results = sql.sql("select * from family.person_ps14 where name like "Pen%" order by Price")
results.show()

//-- Q3
val results = sql.sql("select * from family.person_ps14 where name like "Pen%" order by Price desc, quantity desc ")
results.show()

//-- Q4
val results = sql.sql("select * from family.person_ps14 order by price desc limit 2")
results.show()

