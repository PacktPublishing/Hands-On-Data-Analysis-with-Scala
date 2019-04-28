package handson.example

/**
  * Demonstrates some of Array's features
  */
object ArrayExample {
  def main(args: Array[String]) = {
    val persons = Array(common.Person("Jon", "Doe", 21), common.Person("Alice", "Smith", 20), common.Person("Bob", "Crew", 27)) // construct a Array of Person objects
    persons.foreach(println)
    println

    val personHead = persons.head // first person in the collection
    println(personHead)
    println

    val personAtTwo = persons(2) // person at index 2 (this is same as apply operation)
    println(personAtTwo)
    println

    val personsTail = persons.tail // collection without the first person
    personsTail.foreach(println)
    println

    val personsByAge = persons.sortBy(p => p.age) // sort persons by age
    personsByAge.foreach(println)
    println

    val personsByFname = persons.sortBy(p => p.fname) // sort persons by first name
    personsByFname.foreach(println)
    println

    val (below25, above25) = persons.partition(p => p.age <= 25) // split persons by age
    below25.foreach(println)
    println
    above25.foreach(println)
    println

    val updatePersons = persons.updated(0, common.Person("Jon", "Doe", 20)) // update first element
    updatePersons.foreach(println)
    println
  }

}
