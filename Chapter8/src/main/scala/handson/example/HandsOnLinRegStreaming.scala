package handson.example

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.regression.StreamingLinearRegressionWithSGD

/**
  * Demonstrates Spark streaming linear regression
  */
object HandsOnLinRegStreaming {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("HandsOnLinRegStreaming")
    val ssc = new StreamingContext(conf, Seconds(10))
    val numFeatures = 3
    val model = new StreamingLinearRegressionWithSGD().setInitialWeights(Vectors.zeros(numFeatures))
    val trainingData = ssc.textFileStream("file:/tmp/lin-reg-train-data").map(LabeledPoint.parse).cache()
    trainingData.print() // output training data for debug purpose
    val testData = ssc.textFileStream("file:/tmp/lin-reg-test-data").map(LabeledPoint.parse)
    model.trainOn(trainingData)
    model.predictOnValues(testData.map(lp => (lp.label, lp.features))).print()
    ssc.start()
    ssc.awaitTerminationOrTimeout(1000*60*3) // Wait for the computation to terminate (3 minutes)
  }
}
