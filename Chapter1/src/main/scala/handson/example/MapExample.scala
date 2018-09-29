package handson.example

import collection.mutable.{HashMap,TreeMap,LinkedHashMap}

/**
  * Demonstrates some of Map's features
  */
object MapExample {
  def main(args: Array[String]) = {
    val countryToCurrency = Map(("US" -> "USD"), ("DE" -> "EUR"), ("FR" -> "EUR"), ("IN" -> "INR")) // Mapping from country code to currency code
    println(countryToCurrency)
    println

    println(countryToCurrency.keys) // country codes
    println

    println(countryToCurrency.values) // currency codes
    println

    println(countryToCurrency("US")) // lookup currency code for US
    println

    val numHashMap = HashMap((1->"one"), (2->"two"), (3->"three"), (4->"four"), (5->"five"), (6->"six"), (7->"seven"), (8->"eight"), (9->"nine")) // keys can be in any order
    println(numHashMap)
    println

    numHashMap += (0->"zero") // add new mapping, keys can be any order
    println(numHashMap)
    println

    val numTreeMap = TreeMap((1->"one"), (2->"two"), (3->"three"), (4->"four"), (5->"five"), (6->"six"), (7->"seven"), (8->"eight"), (9->"nine")) // keys must be sorted
    println(numTreeMap)
    println

    numTreeMap += (0->"zero") // add a new mapping, keys must get sorted
    println(numTreeMap)
    println

    val numLinkedHMap = LinkedHashMap((1->"one"), (2->"two"), (3->"three"), (4->"four"), (5->"five"), (6->"six"), (7->"seven"), (8->"eight"), (9->"nine")) // order must be preserved
    println(numLinkedHMap)
    println

    numLinkedHMap += (0->"zero") // this must be the last element
    println(numLinkedHMap)
  }
}
