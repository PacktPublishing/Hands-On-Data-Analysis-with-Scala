package handson.example

import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.Statistics

object PositiveCorrelationExample {
  def getSparkSession(): SparkSession = {
    val spark = SparkSession.builder().master("local").getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    spark
  }

  def main(args: Array[String]): Unit = {
    val spark = getSparkSession()

    val data = spark.sparkContext.parallelize(
      Seq(
        Vectors.dense(1.0, 1.0, 100.0),
        Vectors.dense(2.0, 10.0, 200.0),
        Vectors.dense(3.0, 100.0, 300.0),
        Vectors.dense(4.0, 1000.0, 400.0),
        Vectors.dense(5.0, 10000.0, 500.0),
        Vectors.dense(6.0, 100000.0, 600.0),
        Vectors.dense(7.0, 1000000.0, 700.0),
        Vectors.dense(8.0, 10000000.0, 800.0),
        Vectors.dense(9.0, 100000000.0, 900.0),
        Vectors.dense(9.9, 1000000000.0, 1000.0)
      )
    )
    val summary = Statistics.colStats(data) // Compute column summary statistics
    println(
      s"""Summary:
    ${summary.count} // number of records
    ${summary.mean}  // mean value for each column
    ${summary.min} // column-wise min
    ${summary.max} // column-wise max
    ${summary.normL1} // column-wise norm L1
    ${summary.normL2} // column-wise Euclidean magnitude
    ${summary.variance}  // column-wise variance
    ${summary.numNonzeros}  // column-wise count of non-zero values
        """.stripMargin)

    val corr = Statistics.corr(data)

    println(s"Correlation:\n${corr}")

    spark.stop()
  }

}
