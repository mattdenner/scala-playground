package com.denner.scalaz

class OptionWSpec extends org.scalatest.FunSpec {
  import scalaz._
  import Scalaz._

  describe("some") {
    it("is something")                { some("Value") assert_=== Some("Value") }
    it("can be applied to an object") { "Value".some  assert_=== some("Value") }
  }

  /*
   * Neat: none[T] is actually typesafe at compile time.
   */
  describe("none") {
    it("needs to have the type specified otherwise it errors at compile time!") { none[String] assert_=== None }
    it("two nones are the same (insert joke about habits here)")                { assert(none[Int] == none[String]) }
  }

  /*
   * Option[W].cata is for dealing with values, and their absence, differently.  Note that the handling
   * for the Some is a function, whereas the handling for the None is a lazy value.  The reason is likely
   * to be referential integrity: sticking None into a function should return the same value, so why bother?
   */
  describe("Option[T].cata") {
    it("deals with something") { none[String].cata((s) => "Something", "Nothing")  assert_=== "Nothing" }
    it("deals with nothing")   { some("Hello").cata((s) => "Something", "Nothing") assert_=== "Something" }
  }
  describe("Option[T].fold[B]") {
    it("should behave like cata but clashes with existing fold implementations") { pending }
  }

  /*
   * Option[W].cata can be a bit awkward to read, so enter some:
   */
  describe("Option[T].some.none") {
    it("behaves like cata does with none") { none[String].some((s) => "Something").none("Nothing")  assert_=== "Nothing" }
    it("behaves like cata does with some") { some("Hello").some((s) => "Something").none("Nothing") assert_=== "Something" }
  }

  /*
   * The Option[W].~ method returns the zero for the type if the instance is None, otherwise the value of
   * the Some.  And Option[W].orZero is the wordy equivalent!  I guess this would be used when performing
   * a map on a Seq[Option[W]], where you wanted the None's to be treated like zeroes.
   */
  describe("Option[T].~") {
    it("it's a something if there's a value")    { ~some("Something") assert_=== "Something" }
    it("it's an empty string when none[String]") { ~none[String]      assert_=== "" }
    it("it's 0 when none[Int]")                  { ~none[Int]         assert_=== 0 }
  }
  describe("Option[T].orZero") {
    it("it's a something if there's a value")    { some("Something").orZero assert_=== "Something" }
    it("it's an empty string when none[String]") { none[String].orZero      assert_=== "" }
    it("it's 0 when none[Int]")                  { none[Int].orZero         assert_=== 0 }
  }
}
