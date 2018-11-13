package handson.example.ingest

import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test specs for IngestExample
  */
class IngestExampleSpec extends FlatSpec with Matchers {
  "Ingest of various formats" should "produce same results" in {
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
    assert(normXml === normJson)
    assert(normXml === normCsv)
  }

  "getState" should "return MA for 02701" in {
    assert(IngestExample.getState("02701") === "MA")
  }

  "filter and filterNot API" should "produce same outcome with appropriate conditions" in {
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
    assert(filteredPersons1 === filteredPersons2)
  }

}
