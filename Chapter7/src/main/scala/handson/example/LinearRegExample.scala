package handson.example

import org.apache.spark.sql.SparkSession

/**
  * Linear regression example
  */
object LinearRegExample {
  val homeDir = System.getProperty("user.home")
  def main(args: Array[String]): Unit = {
    // 1. Set Spark session
    val spark = SparkSession.builder().master("local").getOrCreate()

    // 2. Set logging level to WARNING
    spark.sparkContext.setLogLevel("WARN")

    // 3. Import necessary classes from Spark MLLib package that are needed for linear regression
    import org.apache.spark.mllib.linalg.Vectors
    import org.apache.spark.mllib.regression.LabeledPoint
    import org.apache.spark.mllib.regression.LinearRegressionModel
    import org.apache.spark.mllib.regression.LinearRegressionWithSGD

    // 4. Load the data
    val data = spark.sparkContext.textFile(s"${homeDir}/lpsa.data")
    // 5. Parse the data into LabeledPoint and cache
    val parsedData = data.map { line =>
      val parts = line.split(',')
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
    }.cache()

    // 6. Build the model by setting number of iterations, step size
    val numIterations = 100
    val stepSize = 0.00000001
    val model = LinearRegressionWithSGD.train(parsedData, numIterations, stepSize)

    // 7. Evaluate model on training examples and compute training error
    val valuesAndPreds = parsedData.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }
    val MSE = valuesAndPreds.map{ case(v, p) => math.pow((v - p), 2) }.mean()
    println(s"training Mean Squared Error $MSE")

    // 8. Save the model
    model.save(spark.sparkContext, s"{homeDir}/LinearRegressionWithSGDModel")
    // 9. Load the saved model
    val sameModel = LinearRegressionModel.load(spark.sparkContext, s"{homeDir}/LinearRegressionWithSGDModel")
    // 10. Output the model
    println(sameModel)
  }

}
