package handson.example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

/**
  * Data Error Example
  */
object DataErrorExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    import spark.implicits._

    // Create a dummy dataset of records with one column called value.
    // Some records have a valid number while a few have an invalid string.
    val df = List("1", "one", "2", "3", "4").toDF

    // Create a new dataset by adding int_value column that converts value column to int.
    val dfWithInt = df.withColumn("int_value",
      col("value").cast(IntegerType))

    // Add a new Boolean column called has_error to indicate whether there was an error in casting from string to integer
    val dfWithError = dfWithInt.selectExpr("value", "int_value",
      "(value is not null and int_value is null) has_error")

    // Display contents
    dfWithError.show

    spark.stop()
  }
}
