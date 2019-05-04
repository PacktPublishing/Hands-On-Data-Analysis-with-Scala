package handson.example

import org.scalatest.{FlatSpec, Matchers}

/**
  * Test specs for FibonacciExample
  */
class FibonacciExampleSpec extends FlatSpec with Matchers {
  "Fibonacci" should "produce same value for both methods" in {
    assert(FibonacciExample.fibUsingSimpleRecursion(10) === FibonacciExample.fibUsingTailRec(10))
  }
  it should "F(0) = 0, F(1) = 1, F(2) = 1, F(3) = 2, F(23) = 28657" in {
    assert(FibonacciExample.fibUsingSimpleRecursion(0) == 0)
    assert(FibonacciExample.fibUsingSimpleRecursion(1) == 1)
    assert(FibonacciExample.fibUsingSimpleRecursion(2) == 1)
    assert(FibonacciExample.fibUsingSimpleRecursion(3) == 2)
    assert(FibonacciExample.fibUsingSimpleRecursion(23) == 28657)
    assert(FibonacciExample.fibUsingTailRec(0) == 0)
    assert(FibonacciExample.fibUsingTailRec(1) == 1)
    assert(FibonacciExample.fibUsingTailRec(2) == 1)
    assert(FibonacciExample.fibUsingTailRec(3) == 2)
    assert(FibonacciExample.fibUsingTailRec(23) == 28657)
  }
  it should "throw illegal argument for negative values" in {
     a [IllegalArgumentException] should be thrownBy {
      FibonacciExample.fibUsingSimpleRecursion(-1)
    }
    a [IllegalArgumentException] should be thrownBy {
      FibonacciExample.fibUsingTailRec(-1)
    }
  }
}
