package handson.example.extract

import java.io.{BufferedReader, InputStreamReader}
import java.util.function.Consumer

import scala.collection.mutable.ListBuffer

/**
  * An example of pull based data extraction
  */
class DataConsumer extends Consumer[String] {
  val buf = ListBuffer[String]()
  override def accept(t: String): Unit = {
    buf += t
  }
}
object PullExample {
  def main(args: Array[String]): Unit = {
    val reader = new BufferedReader(
      new InputStreamReader(
        new java.net.URL("https://data.lacity.org/api/views/nxs9-385f/rows.csv?accessType=DOWNLOAD").openStream()
      )
    )
    val dataConsumer = new DataConsumer
    reader.lines().forEach(dataConsumer)
    dataConsumer.buf.toList.take(5).foreach(println)
  }

}
