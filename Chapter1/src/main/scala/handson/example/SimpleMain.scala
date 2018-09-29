package handson.example

/**
  * A very simple program that prints how many arguments were received and then prints each argument
  */
object SimpleMain {
  def main(args: Array[String]) = {
    require(!args.isEmpty, "Please provide at least one argument")
    println(s"main method received ${args.length} arguments")
    args.foreach(println)
  }
}
