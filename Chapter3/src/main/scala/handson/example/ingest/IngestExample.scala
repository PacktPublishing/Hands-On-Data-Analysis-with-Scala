package handson.example.ingest

import org.json4s._
import org.json4s.native.JsonMethods._

case class Person(fname: String, lname: String, phone: String, zip: String, state: String) {
  def cleanCopy(): Person = {
    this.copy(fname.trim, lname.trim, phone.trim, zip.trim, state.trim.toUpperCase)
  }
}

/**
  * An example to demonstrate Data Ingestion
  */
object IngestExample {
  def getState(zip: String): String = { // Partial implementation for simplicity
    val zipPerfix = zip.substring(0, 3)
    zipPerfix match {
      case "006" | "007" | "009" => "PR" // PR is Puerto Rico
      case "008" => "VI" // VI is Virgin Islands
      case n if (n.toInt >= 10 && n.toInt <= 27) => "MA" // 010 to 027 is MA
      case "028" | "029" => "RI"
      case n if (n.toInt >= 100 && n.toInt <= 149) => "NY" // 010 to 027 is MA
      case _ => "N/A"
    }
  }

  def populateStateIfNecessary(p: Person): Person = {
    if (p.state == null || p.state.isEmpty)
      p.copy(state = getState(p.zip))
    else
      p
  }

  def main(args: Array[String]): Unit = {
    implicit val formats = DefaultFormats
    // XML
    val xml = <person>
      <fname>Jon</fname>
      <lname>Doe</lname>
      <phone>123-456-7890</phone>
      <zip>12345</zip>
      <state>NY</state>
    </person>
    val normXml = Person(xml \ "fname" text, xml \ "lname" text, xml \ "phone" text, xml \ "zip" text, xml \ "state" text)
    // JSON
    val jsonStr =
      """
                 {
    "fname": "Jon",
    "lname": "Doe",
    "phone": "123-456-7890",
    "zip": "12345",
    "state": "NY"
    }"""
    val json = parse(jsonStr)
    val normJson = json.extract[Person]
    // CSV (for simplicity, we use split method of String to parse CSV)
    val csvStr = "Jon,Doe,123-456-7890,12345,NY"
    val csvCols = csvStr.split(",")
    val normCsv = Person(csvCols(0), csvCols(1), csvCols(2), csvCols(3), csvCols(4))
    // Let us make sure that all normal objects are same
    println(normXml == normJson)
    println(normXml == normCsv)


    val uncleanCsvStr = " Jon , Doe , 123-456-7890 , 12345 , " // missing state
    val uncleanCsvCols = uncleanCsvStr.split(",")
    val uncleanNormCsv = Person(uncleanCsvCols(0), uncleanCsvCols(1), uncleanCsvCols(2), uncleanCsvCols(3), uncleanCsvCols(4))
    val cleanNormCsv = uncleanNormCsv.cleanCopy
    val enriched = populateStateIfNecessary(cleanNormCsv)

    val originalPersons = List(
      Person("Jon", "Doe", "123-456-7890", "12345", "NY"),
      Person("James", "Smith", "555-456-7890", "00600", "PR"),
      Person("Don", "Duck", "777-456-7890", "00800", "VI"),
      Person("Doug", "Miller", "444-456-7890", "02800", "RI"),
      Person("Van", "Peter", "333-456-7890", "02700", "MA")
    )
    val exclusionStates = Set("PR", "VI") // we want to exclude these states
    val filteredPersons1 = originalPersons.filterNot(p => exclusionStates.contains(p.state))
    val filteredPersons2 = originalPersons.filter(p => !exclusionStates.contains(p.state))
    println(filteredPersons1 == filteredPersons2)

  }

}
