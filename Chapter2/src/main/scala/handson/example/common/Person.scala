package handson.example.common

/**
  * Person
  * @param id ID
  * @param fname first name
  * @param lname last name
  * @param age age in years (optional)
  */
case class Person(id: String, fname: String, lname: String, age: Option[Int] = None) {
  // Insure that id, fname and lname are non-empty
  require( !(id.isEmpty || fname.isEmpty || lname.isEmpty), "id/fname/lname cannot be empty")
  // If age ide defined, insure it is non negative
  if (age.isDefined) require(age.get >= 0, "age cannot be negative")
}
