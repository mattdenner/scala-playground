package com.denner.scalaz

class ValidationSpec extends org.scalatest.FunSpec {
  import scalaz._
  import Scalaz._

  /*
   * Validation is not the act of performing a validation, but the result of one.  Therefore it is either
   * a successful or an unsuccessful result.  Sticking to the Scala convention for Either, the successful
   * validation is on the *right* side of the definition.
   */

  /*
   * One of the things that is apparent is that you would save time by naming your types, making the
   * code clearer to read and hopefully less to type.
   */
  type DidItWork = Validation[Int,String]   // Overall validation type
  type Woot      = Success[Int,String]      // The success type
  type Boo       = Failure[Int,String]      // The failure type

  /*
   * 'success' and 'failure' are for constructing success and failure validation instances.  But each
   * only takes a value of it's matching type, which means you have to either tell the compiler what
   * the other is, or make it inferrable.
   */
  describe("success[E,A]") {
    it("creates a success when told what type to use") { success[Int,String]("Woot!") assert_=== Success[Int,String]("Woot!") }
    it("can be inferred from the type")                { val x: Validation[Int,String] = success("Woot!") ; x assert_=== Success[Int,String]("Woot!") }
  }
  describe("failure[E,A]") {
    it("creates a failure when told what type to use") { failure[Int,String](1) assert_=== Failure[Int,String](1) }
    it("can be inferred from the type")                { val x: Validation[Int,String] = failure(1) ; x assert_=== Failure[Int,String](1) }
  }

  /*
   * But there's also a way to create the right type of validation from an Either too:
   */
  describe("validation[E,A]") {
    it("creates a success for a right") { validation(Right[Int,String]("Woot!")) assert_=== Success[Int,String]("Woot!") }
    it("creates a failure for a left")  { validation(Left[Int,String](1))        assert_=== Failure[Int,String](1) }
  }

  val successValue: DidItWork = success("Woot!")
  val failureValue: DidItWork = failure(1)

  /*
   * 'map' and 'foreach' behave pretty much as you'd expect:
   */
  describe("map") {
    it("calls the function for success")         { successValue.map((x) => "Hurrah!") assert_=== Success[Int,String]("Hurrah!") }
    it("does not call the function for failure") { failureValue.map((x) => "Hurrah!") assert_=== failureValue }
  }
  describe("foreach") {
    it("calls the function for success")         { intercept[RuntimeException] { successValue.foreach((x) => throw new RuntimeException("Whoops!")) } }
    it("does not call the function for failure") {                               failureValue.foreach((x) => throw new RuntimeException("Whoops!")) }
  }

  /*
   * When you fold you do different things depending on the success or failure:
   */
  describe("fold") {
    it("can do one thing for success") { successValue.fold((x) => "Failed", (x) => "Succeeded") assert_=== "Succeeded" }
    it("can do another for failure")   { failureValue.fold((x) => "Failed", (x) => "Succeeded") assert_=== "Failed" }
  }

  /*
   * Go back to something standard!
   */
  describe("either") {
    it("returns Right for success") { successValue.either assert_=== Right("Woot!") }
    it("returns Left for failure")  { failureValue.either assert_=== Left(1) }
  }
  describe("toOption[A]") {   // Note that the None here is of the success type
    it("returns Some[A] for success") { successValue.toOption assert_=== some("Woot!") }
    it("returns None[A] for failure") { failureValue.toOption assert_=== none[String] }
  }
}

