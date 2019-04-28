package handson.example

import org.apache.spark.sql.SparkSession

/**
  * Spark based median example
  */
object HandsOnSparkMedian {

  def getMedian(counts: Array[Tuple2[Int, Int]]): Double = {
    val totalCount = counts.map(_._2).sum
    val sortedCounts = counts.sortBy(_._1) // Sort by value
    // Compute range index for each distinct value
    val valuesWithIndex = {
      var currentTotal = 0
      sortedCounts.map(kv => {
        val from = currentTotal;
        currentTotal += kv._2
        val to = currentTotal - 1
        (kv._1, from, to)
      })
    }
    // left index of mid-point
    val leftMidPointIdx = if (totalCount % 2 == 0)
      totalCount / 2 - 1
    else
      totalCount / 2
    val rightMidPointIdx = totalCount / 2 // right mid-point index
    // Filter out the relevant distinct values
    val midPoints = valuesWithIndex.filter(
      v => (leftMidPointIdx >= v._2 && leftMidPointIdx <= v._3) || (rightMidPointIdx >= v._2 && rightMidPointIdx <= v._3)
    )
    midPoints.map(_._1).sum / midPoints.size // Compute median by taking average
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    import spark.implicits._

    // Step 1: Generate 1,000,000 random numbers in range [0, 20) 1000 partitions
    val ds = spark.range(0, 1000000, 1, 1000). // 1a: 1,000,000 numbers starting with 0 with 1000 partitions
      map(i => scala.util.Random.nextInt(20)) // 1b: Randomize
    // Step 2: Map each num as key value pair of (num, count) where count = 1
    val dsWithCount = ds.rdd.map((_, 1))
    // Step 3: Combine the results
    val combined = dsWithCount.reduceByKey(_ + _).collect
    combined.foreach(kv => println(s"${kv._1}=${kv._2}"))

    println("Median", getMedian(combined))

    spark.stop()
  }
}
