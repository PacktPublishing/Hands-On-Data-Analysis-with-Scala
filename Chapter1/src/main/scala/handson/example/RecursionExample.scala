package handson.example

import scala.annotation.tailrec

/**
  * Recursion example using standard and tail recursion
  * [[https://en.wikipedia.org/wiki/Factorial Factorial Wikipedia]]
  * [[https://en.wikipedia.org/wiki/Palindrome Palindrome Wikipedia]]
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

  /**
    * Is Palindrome?
    * @param s input string
    * @return true if input is Palindrome else false
    */
  @tailrec
  def isPalindrome(s: String): Boolean = {
    require(s != null)
    if (s.isEmpty || s.length == 1)
      true
    else if (s(0) != s(s.length-1))
      false
    else
       isPalindrome(s.tail.substring(s.length-1))
  }

  def main(args: Array[String]): Unit = {
    println(factorial(10)) // Expected: 3628800
    println(optimizedFactorial(10)) // Expected: 3628800
    println(isPalindrome("abcdedcba")) // Expected: true
    println(isPalindrome("abcdedcbae")) // Expected: false
  }
}
