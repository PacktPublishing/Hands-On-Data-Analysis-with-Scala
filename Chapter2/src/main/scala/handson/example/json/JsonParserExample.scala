package handson.example.json

import handson.example.common.Person
import org.json4s._
import org.json4s.native.JsonMethods._

/**
  * JSON Parser Example
  */
object JsonParserExample {
  def main(args: Array[String]): Unit = {
    implicit val formats = DefaultFormats
    val personStr = """{
                   "id": "123",
                   "fname": "John",
                   "lname": "Doe",
                   "age": 21
                   }"""
    val json = parse(personStr) // parses JSON string to JValue
    val person = json.extract[Person] // convert to Person object
    println(person)
  }

}
