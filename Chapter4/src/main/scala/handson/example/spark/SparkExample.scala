package handson.example.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkFiles

object SparkExample {
  def getSparkSession(): SparkSession = {
    val spark = SparkSession.builder().master("local").getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    spark
  }
  def main(args: Array[String]): Unit = {
    val spark = getSparkSession()
    spark.sparkContext.addFile("https://data.lacity.org/api/views/nxs9-385f/rows.csv")
    val df = spark.read.option("header", true).option("inferSchema", true).csv(SparkFiles.get("rows.csv"))
    df.printSchema()
    df.show()
    df.sample(0.05).show() // 5% random sample
    println("Covariance: " + df.stat.cov("Total Population", "Total Households"))
    println("Correlation: " + df.stat.corr("Total Population", "Total Households"))

    spark.stop()
  }
}
