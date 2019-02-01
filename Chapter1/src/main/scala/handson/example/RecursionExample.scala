package handson.example

import scala.annotation.tailrec

/**
  * Recursion example using standard and tail recursion
  */
object RecursionExample {

  // standard recursion
  def factorial(n: Int): Long = if (n <= 1) 1 else n * factorial(n-1)

  // tail recursion
  def optimizedFactorial(n: Int): Long = {
    @tailrec
    def factorialAcc(acc: Long, i: Int): Long = {
      if (i <= 1) acc else factorialAcc(acc * i, i -1)
    }
    factorialAcc(1, n)
  }

  def main(args: Array[String]): Unit = {
    println(factorial(10))
    println(optimizedFactorial(10))
  }
}
