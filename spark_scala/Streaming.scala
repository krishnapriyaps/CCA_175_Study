
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._ 


val conf = new SparkConf().setMaster("local[2]").setAppName("Streaming").set("spark.ui.port", "44040")
val sc = new SparkContext(conf)

sc.getConf.setMaster("local[2]").setAppName("Streaming").set("spark.ui.port", "44040")

val ssc = new StreamingContext(sc, Seconds(1))

//Source started in bash: $> nc -lk 9999
val lines = ssc.socketTextStream("localhost", 9999)

val words = lines.flatMap(_.split(" "))

// Count each word in each batch
val pairs = words.map(word => (word, 1))
val wordCounts = pairs.reduceByKey(_ + _)

// Print the first ten elements of each RDD generated in this DStream to the console
wordCounts.saveAsTextFiles("hdfs://quickstart.cloudera:8020/user/cloudera/revist_march_12/streamingout")

ssc.start()             // Start the computation
ssc.awaitTermination()  // Wait for the computation to terminate