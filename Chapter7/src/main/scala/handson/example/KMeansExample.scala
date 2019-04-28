package handson.example

import org.apache.spark.sql.SparkSession

/**
  * K-means clustering example
  */
object KMeansExample {
  val homeDir = System.getProperty("user.home")

  def main(args: Array[String]): Unit = {

    // 1. Set Spark session
    val spark = SparkSession.builder().master("local").getOrCreate()

    // 2. Set logging level to WARNING
    spark.sparkContext.setLogLevel("WARN")

    // 3. Import necessary classes from Spark's MLLib package
    import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
    import org.apache.spark.mllib.linalg.Vectors

    // 4. Load and parse the data
    val data = spark.sparkContext.textFile(s"${homeDir}/kmeans_data.txt")
    val parsedData = data.map(s => Vectors.dense(s.split(' ').map(_.toDouble))).cache()

    // 5. Cluster the data into two classes using KMeans
    val numClusters = 2
    val numIterations = 20
    val clusters = KMeans.train(parsedData, numClusters, numIterations)

    // 6. Evaluate clustering by computing Within Set Sum of Squared Errors
    val WSSSE = clusters.computeCost(parsedData)
    println(s"Within Set Sum of Squared Errors = $WSSSE")

    // 7. Save the model
    clusters.save(spark.sparkContext, s"${homeDir}/KMeansModel")

    // 8. Load the saved model
    val sameModel = KMeansModel.load(spark.sparkContext, s"${homeDir}/KMeansModel")
    println(sameModel)

    spark.stop()

  }

}
