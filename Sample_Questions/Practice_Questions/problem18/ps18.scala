Problem 18

// Read File
val rdd =  sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/testdata/employee.csv")

//Create RDD
case class Employee(id : String, Name : String)
val classRDD = rdd.map(_.split(",")).map(a => Employee(a(0),a(1)))
import org.apache.spark.sql._
val sql = new SQLContext(sc)
val df =  sql.createDataFrame(classRDD)

// Approach1 : Sort with Name and Write to file
df.registerTempTable("employee")
//sort it by name
val resulCSV =  sql.sql("select concat(id,',', Name) from (select * from employee sort by Name) T")
resulCSV.write.format("text").save("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/testdata/employeeSorted.csv")

// Apporach2 : Sort with Name and Write to file
val dfSorted  = df.orderBy("Name")
val rddCSV = dfSorted.map(row => (row(0)+","+ row(1))).coalesce(2)
rddCSV.collect()
rddCSV.saveAsTextFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/testdata/employeeSorted_2.csv")
