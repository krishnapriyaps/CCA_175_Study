
// Read file
val rdd = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/testdata/ps19.csv")

//Create dataframe
case class Patient(patientID : String ,name: String ,dateOfBirth : String ,lastVisitDate:String)
val rddTemp = rdd.map(_.split(",")).map( l => Patient(l(0),l(1),l(2),l(3)))
import org.apache.spark.sql._
val sql = new SQLContext(sc)
val df_temp = sql.createDataFrame(rddTemp)
val df = df_temp.withColumn("dateOfBirth", to_date($"dateOfBirth")).withColumn("lastVisitDate", to_date($"lastVisitDate"))
//register temporary table
df.registerTempTable("patients")

//Find all the patients whose lastVisitDate between current time and '2012-09-15'
val result1 = sql.sql("select * from patients where lastVisitDate < current_date() and lastVisitDate > to_date('2012-09-15')")
>>> result1.show
+---------+-----+-----------+-------------+
|patientID| name|dateOfBirth|lastVisitDate|
+---------+-----+-----------+-------------+
|     1002|Kumar| 2011-10-29|   2012-09-20|
|     1003|  Ali| 2011-01-30|   2012-10-21|
+---------+-----+-----------+-------------+

//Find all the patients who born in 2011
val result2 = sql.sql("select * from patients where year(dateOfBirth) = 2011")
>>> result2.show
+---------+-----+-----------+-------------+
|patientID| name|dateOfBirth|lastVisitDate|
+---------+-----+-----------+-------------+
|     1002|Kumar| 2011-10-29|   2012-09-20|
|     1003|  Ali| 2011-01-30|   2012-10-21|
+---------+-----+-----------+-------------+

//Find all the patients age
val result3 = sql.sql("select patientID,name,dateOfBirth, floor(datediff(current_date(), dateOfBirth) / 365) as age , lastVisitDate from patients ")
>>> result3.show
+---------+-------+-----------+---+-------------+
|patientID|   name|dateOfBirth|age|lastVisitDate|
+---------+-------+-----------+---+-------------+
|     1001|Ah Teck| 1991-12-31| 26|   2012-01-20|
|     1002|  Kumar| 2011-10-29|  6|   2012-09-20|
|     1003|    Ali| 2011-01-30|  7|   2012-10-21|
+---------+-------+-----------+---+-------------+

//  List patients whose last visited more than 60 days ago
val result4 = sql.sql(" select * from patients where datediff(current_date(), lastVisitDate)  > 60")
>>>> result4.show
+---------+-------+-----------+-------------+
|patientID|   name|dateOfBirth|lastVisitDate|
+---------+-------+-----------+-------------+
|     1001|Ah Teck| 1991-12-31|   2012-01-20|
|     1002|  Kumar| 2011-10-29|   2012-09-20|
|     1003|    Ali| 2011-01-30|   2012-10-21|
+---------+-------+-----------+-------------+

// Select patients 18 years old or younger
val result5 = sql.sql(" select * from patients where floor(datediff(current_date(), dateOfBirth) / 365) <= 18")
>>>> result5.show()
+---------+-----+-----------+-------------+
|patientID| name|dateOfBirth|lastVisitDate|
+---------+-----+-----------+-------------+
|     1002|Kumar| 2011-10-29|   2012-09-20|
|     1003|  Ali| 2011-01-30|   2012-10-21|
+---------+-----+-----------+-------------+




