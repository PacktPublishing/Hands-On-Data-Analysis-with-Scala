package handson.example

/**
  * Spark RDD Example
  */
object SparkRDDExample {

  def main(args: Array[String]): Unit = {
    // 0a. Set Spark session
    import org.apache.spark.sql.SparkSession
    val spark = SparkSession.builder().master("local").getOrCreate()

    // 0b. Set logging level to WARNING
    spark.sparkContext.setLogLevel("WARN")

    // 1. Create an RDD of first few prime numbers
    val rdd = spark.sparkContext.parallelize(Seq(2, 3, 5, 7, 11, 13, 17))

    // 2. Get RDD size
    val count = rdd.count()
    println(count)

    // 3. Compute total by using the reduce method of RDD
    val total = rdd.reduce(_+_)
    println(total)

    // 4. Get simple stats on RDD
    val rddStats = rdd.stats()
    println(rddStats)

    // 5. Use collect to bring RDD data to driver
    val rddData = rdd.collect()
    rddData.foreach(println)

    // Stop Spark session before existing
    spark.stop()
  }

}
