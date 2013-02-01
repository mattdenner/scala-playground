package com.denner.scalaz

class EqualSpec extends org.scalatest.FunSpec {
  import scalaz._
  import Scalaz._

  /*
   * I can't quite understand why this is needed at the moment but:
   *
   * Disable scalatest's implicit conversion of a value to a scalatest equal handler, so that we can go about using
   * the Scalaz === and /== functions in here.  Essentially the behaviour is to turn anything of type T into the
   * Identity[T] instance, provided by Scalaz.
   *
   * Scala 2.10 appears to require that these implicit conversions are specifically enabled.
   */
  import scala.language.implicitConversions
  implicit def convertToEqualizer[T](left: T) = mkIdentity(left)

  /*
   * We're going to use this case class to test Equal[W].
   */
  case class OurClass(stringValue: String, intValue: Int)

  /*
   * Equal[T] effectively allows you to extact the equivalence determination into a separate object.  This gives you
   * the flexibility to change how instances of a type are compared.  We start with the obvious field comparisons
   * using the 'equalBy' helper function from Scalaz.
   */
  describe("comparing by name") { performChecks(equalBy(_.stringValue)) }
  describe("comparing by value") { performChecks(equalBy(_.intValue)) }

  /*
   * At this point you're like "so what?" right:
   */
  describe("comparing by a mixture") {
    // Only equal when the stringValues are "One" and the left intValue is half that of the right.
    implicit def OurClassEqualHandler: Equal[OurClass] = equal {
      case (OurClass("One", x), OurClass("One", y)) => x == y/2
      case _                                        => false
    }

    it("makes them inequivalent by stringValue") { assert(OurClass("One", 1) /== OurClass("Two", 2)) }
    it("makes them inequivalent by intValue")    { assert(OurClass("One", 2) /== OurClass("One", 2)) }
    it("makes them inequivalent by order!")      { assert(OurClass("One", 2) /== OurClass("One", 1)) }
    it("makes them equivalent by intValue")      { assert(OurClass("One", 1) === OurClass("One", 2)) }
  }

  def performChecks(implicit OurClassEqualHandler: Equal[OurClass]) = {
    describe("===") {
      it("when they are equivalent it returns true")    { assert(OurClass("One", 1)   === OurClass("One", 1)) }
      it("when they are inequivalent it returns false") { assert(!(OurClass("One", 1) === OurClass("Two", 2))) }
    }
    describe("/==") {
      it("when they are equivalent it returns false")  { assert(!(OurClass("One", 1) /== OurClass("One", 1))) }
      it("when they are inequivalent it returns true") { assert(OurClass("One", 1)   /== OurClass("Two", 2)) }
    }
  }
}
