package handson.example

/**
  * Example to demonstrate running average
  */
object RunningAverage {
  def runningAverage(prevAvgCount: Tuple2[Double, Long], newItems: Array[Int]): Tuple2[Double, Long] = {
    val prevAverage = prevAvgCount._1
    val prevItemCount = prevAvgCount._2
    val newTotal = prevAverage * prevItemCount + newItems.sum
    val newItemCount = prevItemCount + newItems.size
    val newAverage = newTotal / newItemCount
    Tuple2(newAverage, newItemCount)
  }

  def main(args: Array[String]): Unit = {
    var currentAvgCount = Tuple2(0.0, 0L)
    currentAvgCount = runningAverage(currentAvgCount, Array(1,2,3))
    println(currentAvgCount)
    currentAvgCount = runningAverage(currentAvgCount, Array(4))
    println(currentAvgCount)
    currentAvgCount = runningAverage(currentAvgCount, Array(5))
    println(currentAvgCount)
    currentAvgCount = runningAverage(currentAvgCount, Array(6))
    println(currentAvgCount)
  }
}
