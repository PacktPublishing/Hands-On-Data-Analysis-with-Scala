package handson.example.saddle

import java.io.{BufferedReader, InputStreamReader}

import org.saddle.io._

/**
  * Saddle CSV Source class
  * @param url
  */
class SaddleCsvSource(url: String) extends CsvSource {
  val reader = new BufferedReader(new InputStreamReader(new java.net.URL(url).openStream()))
  override def readLine: String = {
    reader.readLine()
  }
}

/**
  * Saddle Example
  */
object SaddlExample {
  def main(args: Array[String]): Unit = {
    val file = new SaddleCsvSource("https://data.lacity.org/api/views/nxs9-385f/rows.csv?accessType=DOWNLOAD")
    CsvParser.parse(file).print()
  }
}
