val input = List( ("Deeapak" , "male", 4000), ("Deepak" , "male", 2000), ("Deepika" , "female",
2000),("Deepak" , "female", 2000), ("Deepak" , "male", 1000) , ("Neeta" , "female", 2000))

val inputRDD = sc.parallelize(input)

val keyValRDD = inputRDD.map(x => (x._1+x._2, x._3))

val sumCombination = keyValRDD.reduceByKey( (a, b) => a+b)

sumCombination.collect
//res11: Array[(String, Int)] = Array((Deepakfemale,2000), (Deeapakmale,4000), (Neetafemale,2000), (Deepakmale,3000), (Deepikafemale,2000)
