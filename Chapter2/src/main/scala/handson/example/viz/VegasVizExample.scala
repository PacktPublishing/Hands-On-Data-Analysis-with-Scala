package handson.example.viz

import vegas._

/**
  * Vegas Viz Example
  */
object VegasVizExample {
  def main(args: Array[String]): Unit = {
    val plot = Vegas("Currency Exchange Rates").
      withData(
        Seq(
          Map("Currency Code" -> "USD", "Exchange Rate" -> 1.00),
          Map("Currency Code" -> "EUR", "Exchange Rate" -> 0.86),
          Map("Currency Code" -> "GBP", "Exchange Rate" -> 0.76),
          Map("Currency Code" -> "CHF", "Exchange Rate" -> 0.99),
          Map("Currency Code" -> "CAD", "Exchange Rate" -> 1.29),
          Map("Currency Code" -> "AUD", "Exchange Rate" -> 1.41),
          Map("Currency Code" -> "HKD", "Exchange Rate" -> 7.83)
        )
      ).
      encodeX("Currency Code", Nom).
      encodeY("Exchange Rate", Quant).
      mark(Point)
    plot.show
  }
}



