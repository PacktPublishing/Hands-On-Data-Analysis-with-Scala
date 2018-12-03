package handson.example.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkFiles
import vegas._
import vegas.sparkExt._


/**
  * Example to demonstrate Spark and Vegas Viz usage
  */
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
    println("Covariance: " + df.stat.cov("Total Population", "Total Households"))
    println("Correlation: " + df.stat.corr("Total Population", "Total Households"))
    df.createOrReplaceTempView("tmp_data")
    val dfWithTier = spark.sql("select *, ntile(100) over(order by `Total Population`) tier from tmp_data")
    val dfTier90Plus = dfWithTier.where("tier >= 90")
    val plot = Vegas().withDataFrame(dfTier90Plus).encodeX("Zip Code", Nom).
      encodeY("Total Population", Quant).
      mark(Bar)
    plot.show

    spark.stop()
  }
}
