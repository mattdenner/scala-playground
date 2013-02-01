package com.denner.scalaz

class OrderSpec extends org.scalatest.FunSpec {
  import scalaz._
  import Scalaz._

  /*
   * We'll use this case class to test ordering
   */
  case class OurClass(stringValue: String, intValue: Int)

  /*
   * As with Equal[T], ordering comes with the convenience stuff:
   */
  describe("when ordering by stringValue") { performChecks(orderBy(_.stringValue)) }
  describe("when ordering by intValue")    { performChecks(orderBy(_.intValue)) }

  /*
   * And you can do more complex ordering:
   */
  describe("complex ordering") {
    implicit def OrderHelper: Order[OurClass] = order {
      case (OurClass("One", x), OurClass("One", y)) => x ?|? y
      case _                                        => LT
    }

    describe("when 'One'") {
      it("is LT when intValues are less than")    { (OurClass("One", 1) ?|? OurClass("One", 2)) assert_=== LT }
      it("is GT when intValues are greater than") { (OurClass("One", 2) ?|? OurClass("One", 1)) assert_=== GT }
      it("is EQ when intValues are equal")        { (OurClass("One", 1) ?|? OurClass("One", 1)) assert_=== EQ }
    }
    it("is LT otherwise") { (OurClass("One", 1) ?|? OurClass("Two", 2)) assert_=== LT }
  }

  def performChecks(implicit OrderHelper: Order[OurClass]) = {
    describe("order") {
      it("returns LT when less than")    { (OurClass("One", 1) ?|? OurClass("Two", 2)) assert_=== LT }
      it("returns GT when greater than") { (OurClass("Two", 2) ?|? OurClass("One", 1)) assert_=== GT }
      it("returns EQ when equal")        { (OurClass("One", 1) ?|? OurClass("One", 1)) assert_=== EQ }
    }
    describe("equal") {
      it("returns true if equal")    { assert(OrderHelper.equal(OurClass("One", 1), OurClass("One", 1))) }
      it("returns false if unequal") { assert(!OrderHelper.equal(OurClass("One", 1), OurClass("Two", 2))) }
    }
  }
}
