package com.denner.scalaz

class CharWSpec extends org.scalatest.FunSpec {
  import scalaz._
  import Scalaz._

  /*
   * Odd that these are not part of scalaz but are required for assert_=== to work correctly here.
   */
  implicit def AlphaEqual: Equal[Alpha] = equalA
  implicit def AlphaShow: Show[Alpha] = showA

  /*
   * Just a couple of methods for turning a character into an object for typesafe usage.  Basically
   * it looks like Digit and Alpha are both so that you can say that a function takes a Digit or an Alpha
   * value, and these provide character conversions to those types.
   */
  describe("digits") {
    it("returns the digit for the character")         { '1'.digit assert_=== some(Digit._1) }
    it("returns none if the character isn't a digit") { 'a'.digit assert_=== none[Digit] }
  }
  describe("alpha") {
    it("returns the alphabetic for the character")       { 'a'.alpha assert_=== some(Alpha.A) }
    it("returns none if the character isn't alphabetic") { '1'.alpha assert_=== none[Alpha] }
  }
}
