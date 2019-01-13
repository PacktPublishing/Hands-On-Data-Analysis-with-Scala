name := "HandsOnScala-Chapter5"

version := "0.1"

// We will use Scala 2.11.x because many of Scala libraries such as
// Spark, vegas-viz are not yet supported for Scala 2.12.x
scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.1.1", // Scala XML library
  "org.json4s" %% "json4s-native" % "3.6.1", // Scala Lift JSON Library
  "org.apache.commons" % "commons-csv" % "1.6", // Apache Commons CSV Java Library
  "org.vegas-viz" %% "vegas-spark" % "0.3.11", // Vegas Viz Library
  "org.scala-saddle" %% "saddle-core" % "1.3.4", // Saddle Dataframe like Library
  "org.apache.spark" %% "spark-sql" % "2.4.0", // Spark Core Library
  "org.apache.spark" %% "spark-mllib" % "2.4.0", // Spark mllib
  "org.scalatest" %% "scalatest" % "3.0.5" % "test" // Scala test library
)
