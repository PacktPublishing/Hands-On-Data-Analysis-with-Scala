package handson.example

import handson.example.common.Person

/**
  * Case class example
  */
object CaseClassExample {

  def isJon(p: common.Person) = {
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

  def main(args: Array[String]): Unit = {
    val jon = Person("Jon", "Doe", 25)
    isJon(jon)
    val bob = Person("Bob", "Crew", 27)
    isJon(bob)
    isJon(null)
  }
}
