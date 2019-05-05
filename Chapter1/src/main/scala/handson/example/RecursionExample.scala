package handson.example

import scala.annotation.tailrec

/**
  * Recursion example using standard and tail recursion
  *
  * [[https://en.wikipedia.org/wiki/Factorial Factorial Wikipedia]]
  * [[https://en.wikipedia.org/wiki/Palindrome Palindrome Wikipedia]]
  */
object RecursionExample {
  /**
    * Factorial of a number
    * @param n input number
    * @return factorial value
    */
  def factorial(n: Int): Long = if (n <= 1) 1 else n * factorial(n-1)

  /**
    * Factorial of a number using tail recursion optimization
    * @param n input number
    * @return factorial value
    */
  def factorialTailRec(n: Int): Long = {
    require(n > 0)
    @tailrec
    def factorialAcc(acc: Long, i: Int): Long = {
      if (i <= 1) acc else factorialAcc(acc * i, i -1)
    }
    factorialAcc(1, n)
  }

  /**
    * Strip out first and last character from a string
    * @param s input string
    * @return stripped out string
    */
  private[this] def stripOutFirstAndLastChar(s: String) = s.tail.dropRight(1)

  /**
    * Is Palindrome?
    * @param s input string
    * @return true if input is Palindrome else false
    */
  @tailrec
  def isPalindromeTailRec(s: String): Boolean = {
    require(s != null)
    if (s.isEmpty || s.length == 1)
      true
    else if (s.charAt(0) != s.charAt(s.length-1))
      false
    else
       isPalindromeTailRec(s.tail.substring(s.length-1))
  }

  def main(args: Array[String]): Unit = {
    println(factorial(10)) // Expected: 3628800
    println(factorialTailRec(10)) // Expected: 3628800
    println(isPalindromeTailRec("abcdedcba")) // Expected: true
    println(isPalindromeTailRec("abcdedcbae")) // Expected: false
  }
}
