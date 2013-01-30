package com.denner.scalaz

class BooleanWSpec extends org.scalatest.FunSpec {
  import scalaz._
  import Scalaz._

  /*
   * I quite like the wordy nature of either.or, as opposed to the imperative 'x ? either : or'.  Note
   * that the return is an Either, which is fine if you're expecting that downstream.
   */
  describe("either.or") {
    it("does the left for true")   { true.either("Either").or("Or")  assert_=== Left("Either") }
    it("does the right for false") { false.either("Either").or("Or") assert_=== Right("Or") }
  }
  describe("? |") {
    it("does the true branch for true")   { (true ? "Either" | "Or")  assert_=== "Either" }
    it("does the false branch for false") { (false ? "Either" | "Or") assert_=== "Or" }
  }

  /*
   * Along side these we have 'option' for Option[T] creation
   */
  describe("option") {
    it("is something when true") { true.option("Something")  assert_=== some("Something") }
    it("is nothing when false")  { false.option("Something") assert_=== none[String] }
  }

  /*
   * 'fold', rather than if...else... when you need a typesafe value.
   */
  describe("fold") {
    it("behaves like if when true")   { true.fold("If", "Else")  assert_=== "If" }
    it("behaves like else when true") { false.fold("If", "Else") assert_=== "Else" }
  }

  /*
   * Discovering that some of these functions are for side-effects, i.e. don't chain!
   */
  describe("when") {
    it("does the side effect for true")         { intercept[RuntimeException] { true.when { throw new RuntimeException } } }
    it("does not do the side effect for false") {                               false.when { throw new RuntimeException }  }
  }
  describe("unless") {
    it("does the side effect for false")       { intercept[RuntimeException] { false.unless { throw new RuntimeException } } }
    it("does not do the side effect for true") {                               true.unless { throw new RuntimeException }    }
  }

  /*
   * There are these sort of zero helpers too.  These might be useful, like the Option[T].toZero.
   */
  describe("??") {
    it("returns the parameter if true")                      { (true ?? "Value")  assert_=== "Value" }
    it("returns the zero for the parameter type when false") { (false ?? "Value") assert_=== "" }
  }
  describe("!?") {
    it("returns the parameter if false")                    { (false !? "Value") assert_=== "Value" }
    it("returns the zero for the parameter type when true") { (true !? "Value")  assert_=== "" }
  }
}
