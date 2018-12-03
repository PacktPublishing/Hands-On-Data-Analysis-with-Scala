package handson.example.saddle

import java.io.{BufferedReader, InputStreamReader}

import org.saddle.io._

class SaddleCsvSource(url: String) extends CsvSource {
  val reader = new BufferedReader(new InputStreamReader(new java.net.URL(url).openStream()))
  override def readLine: String = {
    reader.readLine()
  }
}

object SaddlExample {
  def main(args: Array[String]): Unit = {
    val file = new SaddleCsvSource("https://data.lacity.org/api/views/nxs9-385f/rows.csv?accessType=DOWNLOAD")
    val frameOrig = CsvParser.parse(file)
    // Get the header
    val head = frameOrig.rowSlice(0,1).rowAt(0)
    // Remove header row and attach the header back as column names
    val frame = frameOrig.rowSlice(1, frameOrig.numRows).mapColIndex(i => head.at(i).get)
    // Get random sample of 2% of dataset
    val sample = frame.rfilter(_ => scala.util.Random.nextDouble() < 0.02)
    sample.print()
  }
}
