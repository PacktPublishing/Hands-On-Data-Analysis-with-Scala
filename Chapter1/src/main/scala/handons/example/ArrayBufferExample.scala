package handons.example

import scala.collection.mutable.ArrayBuffer

/**
  * Demonstrate ArrayBuffer usage
  */
object ArrayBufferExample {
  def main(args: Array[String]) = {
    val personsBuf = ArrayBuffer[Person]() // create ArrayBuffer of Person

    personsBuf.append(Person("Jon", "Doe", 21)) // append a Person object at the end

    personsBuf.prepend(Person("Alice", "Smith", 20)) // prepend a Person object at head

    personsBuf.insert(1, Person("Bob", "Crew", 27)) // insert a Person object at index 1

    val persons = personsBuf.toArray // materialize into an Array of Person
    persons.foreach(println)
    println

    val personRemoved = personsBuf.remove(1) // remove Person object at index 1
    println(personRemoved)
    println

    val personsUpdated = personsBuf.toArray // materialize into an Array of Person
    personsUpdated.foreach(println)
  }

}
