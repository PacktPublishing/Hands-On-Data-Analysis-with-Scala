package handson.example

import org.apache.spark._
import org.apache.spark.streaming._

/**
  * Defines a Serializeable running update function
  */
case object RunningUpdate { // this makes the object serializeable
  val updateCount = (newValues: Seq[Int], runningCount: Option[Int]) => {
    val newCount = runningCount.getOrElse(0) + newValues.sum
    Some(newCount): Option[Int]
  }
}

/**
  * Demonstrates Spark Streaming using word count example
  */
object HandsOnSparkStreaming {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("HandsOnSparkStreaming") // least two cores are required to prevent a starvation scenario
    val ssc = new StreamingContext(conf, Seconds(5)) // 5 seconds: the time interval at which streaming data will be divided into batches
    ssc.checkpoint(".") // use current directory for checkpoint
    val lines = ssc.socketTextStream("localhost", 12345) // 12345 is the netcat server port
    val words = lines.flatMap(_.split("\\s+"))
    val pairs = words.map(word => (word, 1))
    val runningCounts = pairs.updateStateByKey[Int](RunningUpdate.updateCount) // apply running update on stream
    runningCounts.print()
    ssc.start() // Start the computation
    ssc.awaitTerminationOrTimeout(1000*60*5) // Wait for 5 minutes before timing out
  }

}
