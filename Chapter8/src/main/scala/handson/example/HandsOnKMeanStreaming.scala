package handson.example

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.mllib.clustering.StreamingKMeans

/**
  * Demonstrates Spark k-means streaming
  */
object HandsOnKMeanStreaming {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("HandsOnKMeanStreaming")
    val ssc = new StreamingContext(conf, Seconds(10))
    val model = new StreamingKMeans().
      setK(4). // number of clusters is 4
      setDecayFactor(1.0). // decay factor (the forgetfulness of the previous centroids)
      setRandomCenters(3, 0.0) // 3 dimensions and 0 weight
    import org.apache.spark.mllib.linalg.Vectors
    val trainingData = ssc.textFileStream("file:/tmp/k-means-train-data").map(Vectors.parse).cache()
    trainingData.print()
    import org.apache.spark.mllib.regression.LabeledPoint
    val testData = ssc.textFileStream("file:/tmp/k-means-test-data").map(LabeledPoint.parse)
    model.trainOn(trainingData)
    model.predictOnValues(testData.map(lp => (lp.label, lp.features))).print()
    ssc.start()
    ssc.awaitTerminationOrTimeout(1000*60*3) // Wait for the computation to terminate (3 minutes)
  }
}
