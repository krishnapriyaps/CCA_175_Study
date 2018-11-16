val salaryRDDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/testdata/salary.txt").map(_.split(",")).map(a => (a(0)+" "+a(1), a(2)))
val technologyRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/cloudera/spark_experiments/testdata/technology.txt").map(_.split(",")).map(a => (a(0)+" "+a(1), a(2)))

val joinedRDD = salaryRDDD.join(technologyRDD)

val resultRDD = joinedRDD.map( row => row._1+"."+row._2._2+"."+row._2._1)
resultRDD.collect
//res10: Array[String] = Array(Mithun kale.spark.150000, Lokesh kumar.unix.95000, Amit Jain.java.100000, Rajni vekat.hadoop.154000, Rahul Yadav.scala.120000)
