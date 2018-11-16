
import org.apache.spark.sql._

val sqlContext  =  new org.apache.spark.sql.hive.HiveContext(sc)

// Create ORC table 
sqlContext.sql("create table  test.product26 (productID int ,productCode String ,name String,quantity int ,price double) stored as ORC")

val dataRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/testdata/product26.csv")

case class Product(productID : Int ,productCode : String ,name : String,quantity : Int ,price : Double)

val productsRDD = dataRDD.map(_.split(",")).map(a => Product(a(0).trim.toInt , a(1), a(2), a(3).trim.toInt,a(4).trim.toDouble))
val df = sqlContext.createDataFrame(productsRDD)

df.registerTempTable("tempProducts")

sqlContext.sql("insert into test.product26  select * from tempProducts")

sqlContext.sql("create table test.product26_par (productID int ,productCode String ,name String,quantity int ,price double) stored as parquet")
sqlContext.sql("insert into test.product26_par  select * from tempProducts")

sqlContext.sql("create table test.product26_avro (productID int ,productCode String ,name String,quantity int ,price double) stored as AVRO")
