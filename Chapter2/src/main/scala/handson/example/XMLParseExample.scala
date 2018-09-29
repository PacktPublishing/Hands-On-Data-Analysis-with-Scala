package handson.example

import scala.xml.Elem

/**
  * Example to demonstrate XML parsing
  */
object XMLParseExample {
  def main(args: Array[String]): Unit = {
    val person: Elem = <person id="123"><fname>John</fname><lname>Doe</lname><age>21</age></person>
    println(person \@ "id")
    println(person \ "fname")
    val lname = person \ "lname"
    println(lname.text)
  }
}
