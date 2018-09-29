package handson.example

import scala.collection.mutable.ListBuffer

/**
  * Demonstrate ListBuffer usage
  */
object ListBufferExample {
  def main(args: Array[String]) = {
    val personsBuf = ListBuffer[Person]() // create ListBuffer of Person

    personsBuf.append(Person("Jon", "Doe", 21)) // append a Person object at end

    personsBuf.prepend(Person("Alice", "Smith", 20)) // prepend a Person object at head

    personsBuf.insert(1, Person("Bob", "Crew", 27)) // insert a Person object at index 1

    val persons = personsBuf.toList // materialize into a List of Person
    println(persons)
    println

    val personRemoved = personsBuf.remove(1) // remove Person object at index 1
    println(personRemoved)
    println

    val personsUpdated = personsBuf.toList // materialize into a List of Person
    println(personsUpdated)

  }

}
