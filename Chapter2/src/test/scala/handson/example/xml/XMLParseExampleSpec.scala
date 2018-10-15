package handson.example.xml

import handson.example.common.Person
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source
import scala.xml.Elem

/**
  * Test specs for XMLParseExample
  */
class XMLParseExampleSpec extends FlatSpec with Matchers {
  "XMLParseExample's parsePerson"  should "handle a simple XML" in {
    val xml: Elem = <person id="123"><fname>John</fname><lname>Doe</lname><age>21</age></person>
    val person = XMLParseExample.parsePerson(xml)
    assert(person === Person("123", "John", "Doe", Some(21)))
  }
  it should "handle missing age" in {
    val xml: Elem = <person id="123"><fname>John</fname><lname>Doe</lname></person>
    val person = XMLParseExample.parsePerson(xml)
    assert(person === Person("123", "John", "Doe"))
  }
  it should "throw NumberFormatException if age is non-integer" in {
    a [NumberFormatException] should be thrownBy {
      val xml: Elem = <person id="123"><fname>John</fname> <lname>Doe</lname> <age>21x</age></person>
      XMLParseExample.parsePerson(xml)
    }
  }
  it should "throw NumberFormatException if age is negative" in {
    a[IllegalArgumentException] should be thrownBy {
      val xml: Elem = <person id="123">
        <fname>John</fname> <lname>Doe</lname> <age>-21</age>
      </person>
      XMLParseExample.parsePerson(xml)
    }
  }
  it should "handle string input" in {
    val xmlStr = Source.fromURL(getClass.getResource("/xml/person.xml")).mkString
    val person = XMLParseExample.parsePerson(xmlStr)
    assert(person === Person("123", "John", "Doe", Some(21)))
  }
}
