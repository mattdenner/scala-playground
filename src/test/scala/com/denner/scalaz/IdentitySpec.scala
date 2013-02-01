package com.denner.scalaz

class IdentitySpec extends org.scalatest.FunSpec {
  import scalaz._
  import Scalaz._

  // See EqualSpec for why this is needed!
  import scala.language.implicitConversions
  implicit def convertToEqualizer[T](left: T) = mkIdentity(left)

  /*
   * Let's test this Identity[T] with a case class, so that we are sure we are only getting the
   * Identity[T] behaviour rather than any type class stuff, and it has some simple rules:
   */
  case class OurClass(name: String)
  implicit def OurClassEqual: Equal[OurClass] = equalBy(_.name)  // equality by name
  implicit def OurClassOrder: Order[OurClass] = orderBy(_.name)  // order by name
  implicit def OurClassShow: Show[OurClass] = showA              // standard display

  /*
   * Identity[T] is backed up by:
   *  - Equal[T] for equality
   *  - Order[T] for ordering
   *  - Show[T] for display
   * And a whole lot of other stuff I've yet to take a look at!
   */

  /*
   * Equality and ordering should be familiar if you've looked at EqualSpec and OrderSpec
   */
  describe("===") {
    it("returns true for equality")    { assert(OurClass("Equal")   === OurClass("Equal")) }
    it("returns false for inequality") { assert(!(OurClass("Equal") === OurClass("Not Equal"))) }
  }
  describe("/==") {
    it("returns false for equality")  { assert(!(OurClass("Equal") /== OurClass("Equal"))) }
    it("returns true for inequality") { assert(OurClass("Equal")   /== OurClass("Not Equal")) }
  }
  describe("?|?") {
    it("returns LT for less than")    { (OurClass("A") ?|? OurClass("B")) assert_=== LT }
    it("returns GT for greater than") { (OurClass("B") ?|? OurClass("A")) assert_=== GT }
    it("returns EQ for equality")     { (OurClass("A") ?|? OurClass("A")) assert_=== EQ }
  }

  /*
   * Some extensions to ordering.
   *
   * And a bit of higher order functions!
   */
  describe("lt")  { performOrderingChecks(_ lt _,  Map(LT -> true,  EQ -> false, GT -> false)) }
  describe("lte") { performOrderingChecks(_ lte _, Map(LT -> true,  EQ -> true,  GT -> false)) }
  describe("gte") { performOrderingChecks(_ gte _, Map(LT -> false, EQ -> true,  GT -> true)) }
  describe("gt")  { performOrderingChecks(_ gt _,  Map(LT -> false, EQ -> false, GT -> true)) }

  def performOrderingChecks(f: (OurClass,OurClass) => Boolean, orderingToResult: Map[Ordering,Boolean]) = {
    val orderedLetters = Map[Ordering,(String,String)](LT -> ("A","B"), EQ -> ("A","A"), GT -> ("B","A"))
    orderingToResult.foreach {
      case (o, r) => it("returns " + r + " for " + o) {
        expectResult(r) { orderedLetters(o).mapElements(OurClass(_), OurClass(_)).fold(f(_,_)) }
      }
    }
  }

  /*
   * Min and max
   */
  describe("min") {
    it("returns the left if that is minimum")  { (OurClass("A") min OurClass("B")) assert_=== OurClass("A") }
    it("returns the right if that is minimum") { (OurClass("B") min OurClass("A")) assert_=== OurClass("A") }
  }
  describe("max") {
    it("returns the left if that is maximum")  { (OurClass("B") max OurClass("A")) assert_=== OurClass("B") }
    it("returns the right if that is maximum") { (OurClass("A") max OurClass("B")) assert_=== OurClass("B") }
  }

  /*
   * Conversions to some of the standard library wrappers:
   */
  describe("some") {
    it("wraps the value in a Some") { OurClass("foo").some assert_=== some(OurClass("foo")) }
  }
  describe("left") {
    it("wraps the value in a Left") { OurClass("foo").left assert_=== Left(OurClass("foo")) }
  }
  describe("right") {
    it("wraps the value in a Right") { OurClass("foo").right assert_=== Right(OurClass("foo")) }
  }
  describe("pair") {
    it("returns the value in a pair tuple") { OurClass("foo").pair assert_=== (OurClass("foo"), OurClass("foo")) }
  }
  describe("squared") {
    it("returns the value in a pair tuple") { OurClass("foo").squared assert_=== (OurClass("foo"), OurClass("foo")) }
  }
}
