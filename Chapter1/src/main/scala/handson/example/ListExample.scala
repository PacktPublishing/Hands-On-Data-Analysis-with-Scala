package handson.example

import annotation.tailrec

/**
  * Demonstrates some of List's features
  */
object ListExample {
  @tailrec def minAgePerson(acc: Option[common.Person], lst: List[common.Person]): Option[common.Person] = {
    if (lst.isEmpty) acc
    else if (acc.isEmpty) minAgePerson(Some(lst.head), lst.tail)
    else if (acc.get.age <= lst.head.age) minAgePerson(acc, lst.tail)
    else minAgePerson(Some(lst.head), lst.tail)
  }

  def main(args: Array[String]) = {
    val persons = List(common.Person("Jon", "Doe", 21), common.Person("Alice", "Smith", 20), common.Person("Bob", "Crew", 27)) // construct a List of Person objects
    println(persons)
    println

    val personHead = persons.head // first person in the collection
    println(personHead)
    println

    val personAtTwo = persons(2) // person at index 2 (this is same as apply operation)
    println(personAtTwo)
    println

    val personsTail = persons.tail // collection without the first person
    println(personsTail)
    println

    val personsByAge = persons.sortBy(p => p.age) // sort persons by age
    println(personsByAge)
    println

    val personsByFname = persons.sortBy(p => p.fname) // sort persons by first name
    println(personsByFname)
    println

    val (below25, above25) = persons.partition(p => p.age <= 25) // split persons by age
    println(below25)
    println(above25)

    val updatePersons = persons.updated(0, common.Person("Jon", "Doe", 20)) // update first element
    println(updatePersons)

    val youngest = minAgePerson(None, persons) // Person with minimum age
    println(youngest)

    val youngestEmpty = minAgePerson(None, Nil) // Nil == List(), an empty list
    println(youngestEmpty)
  }
}
