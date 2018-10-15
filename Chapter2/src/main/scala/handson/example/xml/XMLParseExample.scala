package handson.example.xml

import handson.example.common.Person

import scala.xml.{Elem, XML}

/**
  * Example to demonstrate XML parsing
  */
object XMLParseExample {

  /**
    * Parse an XML to Person object
    * @param xml
    * @return Person object
    */
  def parsePerson(xml: Elem): Person = {
    val id = xml \@ "id"
    val fname = xml \ "fname"
    val lname = xml \ "lname"
    val age = xml \ "age"
    val ageInYears = if (age.isEmpty) None else Some(age.text.toInt)
    Person(id, fname.text, lname.text, ageInYears)
  }

  def parsePerson(xmlStr: String): Person = {
    val xml = XML.loadString(xmlStr)
    parsePerson(xml)
  }

  def main(args: Array[String]): Unit = {
    val personXml: Elem = <person id="123"><fname>John</fname><lname>Doe</lname><age>21</age></person>
    val id = personXml \@ "id"
    println(s"id=${id}, isEmpty=${id.isEmpty}")
    val fname = personXml \ "fname"
    println(fname)
    val lname = personXml \ "lname"
    println(lname.text)
    val age = personXml \ "age"
    println(age.text)
    val person = Person(id, fname.text, lname.text, Some(age.text.toInt))
    println(person)
    print(parsePerson(personXml))
  }
}
