package handson.example

import scala.annotation.tailrec

/**
  * Fibonacci Example using simple as well as tail recursion
  *
  * F(0) = 0, F(1) = 1
  * For n > 1, F(n) = F(n-1) + F(n-2)
  */
object FibonacciExample {
  /**
    * Fibonacci using simple recursion
    * @param n input number
    * @return Fibonacci(n)
    */
  def fibUsingSimpleRecursion(n: Int): Int = {
    require(n >= 0)
    if (n == 0)
      0
    else if (n == 1)
      1
    else
      fibUsingSimpleRecursion(n-1) + fibUsingSimpleRecursion(n-2)
  }

  /**
    * Fibonacci using tail recursion
    * @param n input number
    * @return Fibonacci(n)
    */
  def fibUsingTailRec(n: Int): Int = {
    require(n >= 0)

    @tailrec
    def fibWithAcc(i: Int, acc: List[Int]): Int = {
      val nextValue = acc.head + acc.tail.head
      if (i == 2)
        nextValue
      else {
        fibWithAcc(i-1, nextValue::acc)
      }
    }

    if (n == 0)
      0
    else if (n == 1)
      1
    else
      fibWithAcc(n, List(1, 0)) // List(F(1), F(0))
  }

  def main(args: Array[String]): Unit = {
    val n = 31 // Expected: 1,346,269
    println(s"${n}: ${fibUsingSimpleRecursion(n)}")
    println(s"${n}: ${fibUsingTailRec(n)}")
  }

}
