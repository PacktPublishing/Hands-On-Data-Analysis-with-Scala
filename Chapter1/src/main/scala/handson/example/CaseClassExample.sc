/**
  * Scala Worksheet for Case Class Example: run and explore in IntelliJ
  */

case class Person(fname: String, lname: String, age: Int)
def isJon(p: Person) = {
  p match {
    case Person("Jon", _, _) => {
      println("I am Jon"); true
    }
    case Person(n, _, _) => {
      println(s"I am not Jon but I am ${n}"); false
    }
    case _ => {
      println("I am not Jon but I am something other than Person"); false
    }
  }
}

val jon = Person("Jon", "Doe", 25)
isJon(jon)
val bob = Person("Bob", "Crew", 27)
isJon(bob)
isJon(null)