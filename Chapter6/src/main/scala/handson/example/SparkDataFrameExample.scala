package handson.example

/**
  * Spark DataFrame Example
  */
object SparkDataFrameExample {

  def main(args: Array[String]): Unit = {
    // 0a. Set Spark session
    import org.apache.spark.sql.SparkSession
    val spark = SparkSession.builder().master("local").getOrCreate()
    import spark.implicits._

    // 0b. Set logging level to WARNING
    spark.sparkContext.setLogLevel("WARN")

    // 1. Create a DataFrame of first few prime numbers
    val df = spark.createDataFrame(Seq(
      (2, "two"),
      (3, "three"),
      (5, "five"),
      (7, "seven"),
      (11, "eleven"),
      (13, "thirteen"),
      (17, "seventeen")
    )).toDF("num", "description")

    // 1a. Print schema
    df.printSchema()

    // 2. Get DataFrame size
    val count = df.count()
    println(count)

    // 3. Compute total by using the selectExpr method of DataFrame
    val total = df.selectExpr("sum(num) total").collect()(0)(0).asInstanceOf[Long]
    println(total)

    // 4. Use collect to bring DataFrame data to driver
    val dfData = df.collect()
    dfData.foreach(println)

    // Stop Spark session before existing
    spark.stop()
  }

}
