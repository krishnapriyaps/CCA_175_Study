import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

// To find count against each key 
object Problem12{

	def main(args : Array[String]){
		val conf = new SparkConf().setAppName("Problem1")
		val sc = new SparkContext(conf)
		
		val keysWithValuesList = Array ("foo=A","foo=A","foo=A","foo=A","foo=B","bas=C","bas=D","bas=D", "cat=1", "dog=I", "dog=II")
		val dataRDD = sc.parallelize(keysWithValuesList)
		println("................................")
		println(dataRDD.partitions.size)
		val keyValPairRDD = dataRDD.map(_.split("=")).map(v => (v(0),v(1))).cache()

		// val groupByKeyRDD = keyValPairRDD.groupByKey()
		// groupByKeyRDD.collect().foreach(println)
		
		val initialCount=0;
		
		val addToCounts = (n : Int, v:String) => n + 1
		val SumPartialCounts = (p1 : Int, p2: Int) => p1 + p2

		val countByKey=keyValPairRDD.aggregateByKey(initialCount)(addToCounts,SumPartialCounts)
		countByKey.collect().foreach(println)
		println("done")
	}
}

// Actual Program and response
// initialCount=0 ; val addToCounts = (n : Int, v:String) => n + 1 ;SumPartialCounts = (p1 : Int, p2: Int) => p1 + p2
// keysWithValuesList = Array ("foo=A","foo=A","foo=A","foo=A","foo=B","bas=C","bas=D","bas=D", "cat=1")
//(bas,3)
//(foo,5)
//(cat,1)
// initialCount=0 ; val addToCounts = (n : Int, v:String) => n + 1 ;SumPartialCounts = (p1 : Int, p2: Int) => p1 + p2
// keysWithValuesList = Array ("foo=A","foo=A","foo=A","foo=A","foo=B","bas=C","bas=D","bas=D", "cat=1", "dog=I", "dog=II")
//(bas,3)
//(foo,5)
//(dog,2)
//(cat,1)

// Different variations in algorithm and its observations

// addToCounts changes
// initialCount=0; val addToCounts = (n : Int, v:String) => n + 2;SumPartialCounts = (p1 : Int, p2: Int) => p1 + p2
// keysWithValuesList = Array ("foo=A","foo=A","foo=A","foo=A","foo=B","bas=C","bas=D","bas=D", "cat=1")
//(bas,6)
//(foo,10)
//(cat,2)
// initialCount=0; val addToCounts = (n : Int, v:String) => n + 2;SumPartialCounts = (p1 : Int, p2: Int) => p1 + p2
// keysWithValuesList = Array ("foo=A","foo=A","foo=A","foo=A","foo=B","bas=C","bas=D","bas=D", "cat=1", "dog=I", "dog=II")
//(bas,6)
//(foo,10)
//(dog,4)
//(cat,2)

//initialCount changes
// initialCount=10; val addToCounts = (n : Int, v:String) => n + 1;SumPartialCounts = (p1 : Int, p2: Int) => p1 + p2
// keysWithValuesList = Array ("foo=A","foo=A","foo=A","foo=A","foo=B","bas=C","bas=D","bas=D", "cat=1")
//(bas,13)
//(foo,25)
//(cat,11)
// initialCount=10; val addToCounts = (n : Int, v:String) => n + 1;SumPartialCounts = (p1 : Int, p2: Int) => p1 + p2
// keysWithValuesList = Array ("foo=A","foo=A","foo=A","foo=A","foo=B","bas=C","bas=D","bas=D", "cat=1", "dog=I", "dog=II")
//(bas,13)
//(foo,15)
//(dog,12)
//(cat,11)

//SumPartialCounts changes
// initialCount=0 ; val addToCounts = (n : Int, v:String) => n + 1 ;SumPartialCounts = (p1 : Int, p2: Int) => p1 + p2 +1
// keysWithValuesList = Array ("foo=A","foo=A","foo=A","foo=A","foo=B","bas=C","bas=D","bas=D", "cat=1")
//(bas,3)
//(foo,6)
//(cat,1)
// initialCount=0 ; val addToCounts = (n : Int, v:String) => n + 1 ;SumPartialCounts = (p1 : Int, p2: Int) => p1 + p2 +1 ; 
// keysWithValuesList = Array ("foo=A","foo=A","foo=A","foo=A","foo=B","bas=C","bas=D","bas=D", "cat=1", "dog=I", "dog=II")
//(bas,3)
//(foo,5)
//(dog,2)
//(cat,1)
// initialCount=0 ; val addToCounts = (n : Int, v:String) => n + 1 ;SumPartialCounts = (p1 : Int, p2: Int) => p1 + p2 +10
// keysWithValuesList = Array ("foo=A","foo=A","foo=A","foo=A","foo=B","bas=C","bas=D","bas=D", "cat=1")
//(bas,3)
//(foo,15)
//(cat,1)
// initialCount=0 ; val addToCounts = (n : Int, v:String) => n + 1 ;SumPartialCounts = (p1 : Int, p2: Int) => p1 + p2 +10 ; 
// keysWithValuesList = Array ("foo=A","foo=A","foo=A","foo=A","foo=B","bas=C","bas=D","bas=D", "cat=1", "dog=I", "dog=II")
//(bas,3)
//(foo,5)
//(dog,2)
//(cat,1)




