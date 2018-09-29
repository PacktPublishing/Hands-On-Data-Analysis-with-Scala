package handson.example

import org.scalatest._

/**
  * Test specifications for Factorial
  */
class FactorialExampleSpec extends FlatSpec with Matchers {
  "Factorial" should "produce same value for both methods" in {
    assert(FactorialExample.classicFactorial(10) === FactorialExample.tailRecFactorial(10))
  }
  it should "throw illegal argument for 0 or negative values" in {
    a [IllegalArgumentException] should be thrownBy {
      FactorialExample.classicFactorial(0)
    }
    a [IllegalArgumentException] should be thrownBy {
      FactorialExample.classicFactorial(-1)
    }
    a [IllegalArgumentException] should be thrownBy {
      FactorialExample.tailRecFactorial(0)
    }
    a [IllegalArgumentException] should be thrownBy {
      FactorialExample.tailRecFactorial(-1)
    }
  }

}
