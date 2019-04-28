package handson.example

/**
  * Vector Level Stats Example
  */
object VectorStatsExample {
  def main(args: Array[String]): Unit = {
    // 0a. Set Spark session
    import org.apache.spark.sql.SparkSession
    val spark = SparkSession.builder().master("local").getOrCreate()
    import spark.implicits._

    // 0b. Set logging level to WARNING
    spark.sparkContext.setLogLevel("WARN")

    // 1. Import necessary classes from Spark ml package
    import org.apache.spark.ml.linalg.{Vector, Vectors}
    import org.apache.spark.ml.stat.Summarizer

    // 2. Create a DataFrame of three feature vectors and weight
    val df = Seq(
      (Vectors.dense(1.0, 2.0, 3.0), 9.0),
      (Vectors.dense(4.0, 5.0, 6.0), 5.0),
      (Vectors.dense(7.0, 8.0, 9.0), 1.0),
      (Vectors.dense(0.0, 1.0, 2.0), 7.0)
    ).toDF("features", "weight")

    //3. Display contents of DataFrame
    df.show(truncate=false)

    // 3. Use the SummaryBuilder build mean, variance metrics using features and weight columns
    // Summarizer's metrics returns SummaryBuilder object that provides summary statistics about a given column.
    val summarizer = Summarizer.metrics("min", "max", "mean", "variance").summary($"features", $"weight")
    println(summarizer)

    //4. Appply summarizer to source DataFrame
    val summaryDF = df.select(summarizer.as("summary"))
    summaryDF.printSchema()

    //5. Display contents of Summary DataFrame
    summaryDF.show(truncate=false)

    //6. Extract mean and variance with weight from Summary DataFrame
    val (min1, max1, meanWithWeight1, varianceWithWeight1) = summaryDF.select("summary.min", "summary.max", "summary.mean", "summary.variance").as[(Vector, Vector, Vector, Vector)].first()
    println(min1, max1, meanWithWeight1, varianceWithWeight1)

    //7. Compute mean and variance with weight using another approach
    val (min2, max2, meanWithWeight2, varianceWithWeight2) = df.select(Summarizer.min($"features"), Summarizer.max($"features"), Summarizer.mean($"features", $"weight"), Summarizer.variance($"features", $"weight")).as[(Vector, Vector, Vector, Vector)].first()
    println(min2, max2, meanWithWeight2, varianceWithWeight2)

    //8. Compute simple mean and variance without weight
    val (min, max, mean, variance) = df.select(Summarizer.min($"features"), Summarizer.max($"features"), Summarizer.mean($"features"), Summarizer.variance($"features")).as[(Vector, Vector, Vector, Vector)].first()
    println(min, max, mean, variance)

    // Stop Spark Session before exiting
    spark.stop()
  }

}
