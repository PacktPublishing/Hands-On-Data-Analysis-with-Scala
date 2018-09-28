/**
  * Scala Worksheet: run and explore in IntelliJ
  */

// Explore class in Scala
class Vehicle(vin: String, manufacturer: String, model: String, modelYear: Int,
              finalAssemblyCountry: String)
// Instantiate a Vehicle object and explore
val theAuto = new Vehicle("WAUZZZ8K6AA123456", "Audi", "A4",
  2009, "Germany")
theAuto.isInstanceOf[Vehicle]
theAuto.isInstanceOf[Int]

// Explore case class in Scala
case class Person(fname: String, lname: String, age: Int)

val thePerson = Person("Peter", "Parker", 21) // note that there is no "new"
thePerson.isInstanceOf[Person]
thePerson.isInstanceOf[Int]
thePerson.age // all constructor args are accessible

// Explore pattern matching with case class
def isJon(p: Person) = {
  p match {
    case Person("Jon", _, _) => {println("I am Jon"); true}
    case Person(n,_,_) => {println(s"I am not Jon but I am ${n}"); false}
    case _ => {println("I am not Jon but I am something other than Person"); false}
  }
}

val jon = Person("Jon", "Doe", 25)

isJon(jon)

val bob = Person("Bob", "Crew", 27)

isJon(bob)

isJon(null)

// Explore functions
val addAndInc = (a: Int, b: Int) => a + b + 1
addAndInc(5, 10)

// Explore List
val someStates = List("NJ", "CA", "IN", "MA", "NY", "AZ", "PA")

someStates.size

someStates.sorted

someStates.reverse

someStates.mkString(",")

someStates.sorted.mkString(",")

