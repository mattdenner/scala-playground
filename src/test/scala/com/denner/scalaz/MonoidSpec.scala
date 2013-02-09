package com.denner.scalaz

class MonoidSpec extends org.scalatest.FunSpec {
  import scalaz._, Scalaz._

  /*
   * We'll test Monoid[A] with our own class:
   */
  case class IntWrapper(value: Int)
  implicit val IntWrapperEqual: Equal[IntWrapper] = equalBy(_.value)
  implicit val IntWrapperShow: Show[IntWrapper]   = showBy(_.value)

  /*
   * Test an implicit monoid that will double the integer values before adding them together, just to
   * prove that '|+|' here, from Semigroup, is not addition!
   */
  it("allows for the monoid to be used implicitly") {
    implicit val intWapperMonoid = new Monoid[IntWrapper] {
      def append(left: IntWrapper, right: => IntWrapper) = IntWrapper((2*left.value) + (2*right.value))
      val zero = IntWrapper(0)
    }
    (IntWrapper(1) |+| IntWrapper(2)) assert_=== IntWrapper(6)
  }

  /*
   * There's a convenience function for building monoids from semigroups and zeroes, but you have to
   * extend the MonoidLow class (and it is a class, not a trait).
   */
  describe("MonoidLow.monoid") {
    new MonoidLow {
      it("allows you to build a Monoid[A] from a Semigroup[A] and a Zero[A]") {
        implicit val intWrapperMonoid = monoid(
          semigroup[IntWrapper]((a,b) => IntWrapper(a.value * b.value)),
          zero(IntWrapper(1))
        )

        (IntWrapper(1) |+| IntWrapper(2)) assert_=== IntWrapper(2)
      }
    }
  }
}

