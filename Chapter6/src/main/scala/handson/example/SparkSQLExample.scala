package handson.example

case class Person(fname: String, lname: String, age: Int)

/**
  * Spark SQL Example
  */
object SparkSQLExample {
  def main(args: Array[String]): Unit = {
    // 0a. Set Spark session
    import org.apache.spark.sql.SparkSession
    val spark = SparkSession.builder().master("local").getOrCreate()
    import spark.implicits._

    // 0b. Set logging level to WARNING
    spark.sparkContext.setLogLevel("WARN")

    // 1. Create a Dataset of Persons
    val personsDS = List(Person("Jon", "Doe", 22), Person("Jack",
      "Sparrow", 35), Person("James", "Bond", 47), Person("Mickey", "Mouse",
      13)).toDS

    // 2. Create a temp view of persons
    personsDS.createOrReplaceTempView("persons")

    // 3. Query persons view to get all records where age >= 21
    val personsAbove21 = spark.sql("select * from persons where age >= 21")

    // 4. Show results
    personsAbove21.show()

    // 5. Run another SQL on the temporary view. Change fname and lname fields to upper case
    val personsUpperCase = spark.sql("select upper(fname) as ufname, upper(lname) ulname, age from persons")

    // 6. Show results
    personsUpperCase.show()

    spark.stop()

  }
}
