package handson.example

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.sql.SparkSession

/**
  * Negative Correlation Example
  */
object NegativeCorrelationExample {
  def getSparkSession(): SparkSession = {
    val spark = SparkSession.builder().master("local").getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    spark
  }

  def main(args: Array[String]): Unit = {
    val spark = getSparkSession()

    val data = spark.sparkContext.parallelize(
      Seq(
        Vectors.dense(0.0, 1.0, 100.0),
        Vectors.dense(-10.0, 10.0, 200.0),
        Vectors.dense(-20.0, 100.0, 300.0),
        Vectors.dense(-30.0, 1000.0, 400.0),
        Vectors.dense(-40.0, 10000.0, 500.0),
        Vectors.dense(-50.0, 100000.0, 600.0),
        Vectors.dense(-60.0, 1000000.0, 700.0),
        Vectors.dense(-70.0, 10000000.0, 800.0),
        Vectors.dense(-80.0, 100000000.0, 900.0),
        Vectors.dense(-90.0, 1000000000.0, 1000.0)
      )
    )

    val corr = Statistics.corr(data)

    println(s"Correlation:\n${corr}")

    spark.stop()
  }

}
