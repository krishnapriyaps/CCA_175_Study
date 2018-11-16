val listData = List( ("Deepak" , "male", 4000), ("Deepak" , "male", 2000), ("Deepika" , "female",2000),("Deepak" , "female", 2000), ("Deepak" , "male", 1000) , ("Neeta" , "female", 2000))

val listRDD =  sc.parallelize(listData).map(a => (a._1+","+a._2, a._3) )

val sumOfKeys = listRdd.reduceByKey((a1, a2) => a1 +a2)


