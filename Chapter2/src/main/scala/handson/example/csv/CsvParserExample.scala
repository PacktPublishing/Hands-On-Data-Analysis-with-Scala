package handson.example.csv

import java.io.{BufferedReader, InputStreamReader}
import java.util.function.Consumer

import org.apache.commons.csv.{CSVFormat, CSVPrinter, CSVRecord}

import scala.collection.mutable.ListBuffer

/**
  * Census Data
  * @param zipCode
  * @param totalPopulation
  * @param medianAge
  * @param totalMales
  * @param totalFemales
  * @param totalHouseholds
  * @param averageHouseholdSize
  */
case class CensusData(zipCode: String, totalPopulation: Int, medianAge: Double,
                      totalMales: Int, totalFemales: Int, totalHouseholds: Int, averageHouseholdSize: Double)

/**
  * Data Consumer that can consume a CSV record, transform and save as Census Data
  */
class DataConsumer extends Consumer[CSVRecord] {
  val buf = ListBuffer[CensusData]()
  override def accept(t: CSVRecord): Unit = {
    buf += CensusData(t.get(0), t.get(1).toInt, t.get(2).toDouble,
      t.get(3).toInt, t.get(4).toInt, t.get(5).toInt, t.get(6).toDouble)
  }
}

/**
  * CSV Parser Example
  */
object CsvParserExample {
  def main(args: Array[String]): Unit = {
    val reader = new BufferedReader(
      new InputStreamReader(
        new java.net.URL("https://data.lacity.org/api/views/nxs9-385f/rows.csv?accessType=DOWNLOAD").openStream()
      )
    )
    val csvParser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader)
    val dataConsumer = new DataConsumer
    csvParser.forEach(dataConsumer)
    val allRecords = dataConsumer.buf.toList
    allRecords.take(3).foreach(println)

    val csvPrinter = new CSVPrinter(System.out, CSVFormat.RFC4180.withHeader("fname", "lname", "age"))
    csvPrinter.printRecord("Jon", "Doe", "21")
    csvPrinter.printRecord("James", "Bond", "39")
    csvPrinter.flush()

  }

}
