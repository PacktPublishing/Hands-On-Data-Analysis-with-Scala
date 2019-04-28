package handson.example

case class PrimeNumber(num: Int, description: String)

/**
  * Spark Dataset Example
  */
object SparkDatasetExample {
  def main(args: Array[String]): Unit = {
    // 0a. Set Spark session
    import org.apache.spark.sql.SparkSession
    val spark = SparkSession.builder().master("local").getOrCreate()
    import spark.implicits._

    // 0b. Set logging level to WARNING
    spark.sparkContext.setLogLevel("WARN")

    // 1. Create a Dataset of first few prime numbers
    val ds = Seq(
      PrimeNumber(2, "two"),
      PrimeNumber(3, "three"),
      PrimeNumber(5, "five"),
      PrimeNumber(7, "seven"),
      PrimeNumber(11, "eleven"),
      PrimeNumber(13, "thirteen"),
      PrimeNumber(17, "seventeen")
    ).toDS()

    // 1a. Print schema
    ds.printSchema()

    // 2. Get Dataset size
    val count = ds.count()
    println(count)

    // 3a. Compute total by using the selectExpr method of Dataset
    val total1 = ds.selectExpr("sum(num) total").collect()(0)(0).asInstanceOf[Long]
    println(total1)


    // 3b. Compute total by using the map and reduce methods of Dataset
    val total2 = ds.map(_.num).reduce(_+_)
    println(total2)

    // 4. Use collect to bring Dataset data to driver
    val dsData = ds.collect()
    dsData.foreach(println)

    // Stop Spark session before existing
    spark.stop()
  }
}
