package handson.example

import annotation.tailrec

/**
  * Factorial example using classic vs tailrec optimizations
  */
object FactorialExample {
  def classicFactorial(n: Int): Int = {
    require(n > 0, "n must be non-zero and positive")
    if (n == 1) n else n * classicFactorial(n-1)
  }

  def tailRecFactorial(n: Int): Int = {
    require(n > 0, "n must be non-zero and positive")
    @tailrec def factorial(acc: Int, m: Int): Int = if (m == 1) acc else factorial(acc * m, m-1) // this should work as this recursive algorithm meets tail recursion requirements
    factorial(1, n)
  }

  def main(args: Array[String]) = {
    val classicResult = classicFactorial(5)
    println(classicResult)
    val tailRecResult = tailRecFactorial(5)
    println(tailRecResult)

  }

}
