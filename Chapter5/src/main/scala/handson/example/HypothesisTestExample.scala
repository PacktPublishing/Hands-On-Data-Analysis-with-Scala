package handson.example

/**
  * Hypothesis Testing Example
  */
object HypothesisTestExample {
  def main(args: Array[String]): Unit = {
    // 1a. Set Spark session
    import org.apache.spark.sql.SparkSession
    val spark = SparkSession.builder().master("local").getOrCreate()
    import spark.implicits._

    // 1b. Set logging level to WARNING
    spark.sparkContext.setLogLevel("WARN")

    //2. Import the necessary classes from Spark’s MLLib package:
    import org.apache.spark.mllib.linalg._
    import org.apache.spark.mllib.stat.Statistics

    //3. Create a sample observation of vectors:
    val observations = Vectors.dense(0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1)

    // 4. Run the chi-square test on the data:
    val results = Statistics.chiSqTest(observations)
    println(results)

    // Stop Spark Session before exiting
    spark.stop()
  }

}