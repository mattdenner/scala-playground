package com.denner.scala

class TupleWSpec extends org.scalatest.FunSpec {
  import scalaz._
  import Scalaz._

  /*
   * Tuples, in general, are extended to add some fairly useful methods.  Here we only work with Tuple2, but they are
   * extended all the way to Tuple11!
   */
  describe("fold") {
    it("calls a two parameter function") { ("a","b").fold((x,y) => "Worked") assert_=== "Worked" }
  }
  describe("mapElements") {
    it("acts like map, except you can map each value independently") { ("a", "b").mapElements((x) => 1, (y) => true) assert_=== (1, true) }
  }
  describe("toIndexedSeq") {
    it("is just a conversion to a sequence") { ("a", "b").toIndexedSeq assert_=== IndexedSeq("a", "b") }
    it("obviously deals with different element types") { pending } // No Equal[IndexedSeq[Any]]: ("a", true).toIndexedSeq assert_=== IndexedSeq("a", true)
  }
}
