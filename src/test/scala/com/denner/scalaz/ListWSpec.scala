package com.denner.scalaz

class ListWSpec extends org.scalatest.FunSpec {
  import scalaz._
  import Scalaz._

  describe("nil[T]") {
    it("returns the empty list") { List[Int]() assert_=== nil[Int] }
  }

  /*
   * List[T].intersperse and List[T].intercolate are kind of closely related, in that the latter
   * is kind of the List[T].intersperse.flatten, if you could do that.
   */
  describe("List[T].intersperse") {
    it("puts the joiner between the values") { List("a","b","c").intersperse(":") assert_=== List("a",":","b",":","c") }
    it("works only with matching types")     { List(1,2,3).intersperse(9)         assert_=== List(1,9,2,9,3) }
  }
  describe("List[T].intercalate") {
    it("puts the joiner between the values") { List("a","b","c").intercalate(List("f","g")) assert_=== List("a","f","g","b","f","g","c") }
    it("works only with matching types")     { List(1,2,3).intercalate(List(8,9))           assert_=== List(1,8,9,2,8,9,3) }
  }

  /*
   * The usual kind of list-of-list generators
   */
  describe("List[T].pairs") {
    it("is empty for empty list")      { nil[Int].pairs           assert_=== nil[(Int,Int)] }
    it("empty for a singular list")    { List(1).pairs            assert_=== nil[(Int,Int)] }
    it("generates all possible pairs") { List(1,2,3).pairs.sorted assert_=== List((1,2),(1,3),(2,3)).sorted }
  }
  describe("List[T].powerset") {
    it("is a list of the empty list for an empty list") { nil[Int].powerset.toList assert_=== List(nil[Int]) }
    it("generates all lists")                           { List(1,2,3).powerset     assert_=== List(List(1,2,3), List(1,2), List(1,3), List(1), List(2,3), List(2), List(3), List()) }
  }
  describe("List[T].tails") {
    it("is a list of the empty list for an empty list") { nil[Int].tails.toList    assert_=== List(nil[Int]) }
    it("generates all tails")                           { List(1,2,3).tails.toList assert_=== List(List(1,2,3), List(2,3), List(3), List()) }
  }

  /*
   * I can't see why this would be here, but these two functions are a combination of map and fold.
   */
  describe("List[T].mapAccumLeft[B,C]") {
    it("accumulates from left & maps") { List(1,2,3).mapAccumLeft("", (c: String, a: Int) => (c ++ a.toString, a * 2)) assert_=== ("123", List(2,4,6)) }
  }
  describe("List[T].mapAccumRight[B,C]") {
    it("accumulates from right & maps") { List(1,2,3).mapAccumRight("", (c: String, a: Int) => (c ++ a.toString, a * 2)) assert_=== ("321", List(2,4,6)) }
  }

  describe("List[T].stripPrefix") {
    it("returns the list afer prefix removal")   { List(1,2,3).stripPrefix(List(1,2)) assert_=== List(3).some }
    it("returns none when prefix doesn't match") { List(1,2,3).stripPrefix(List(2,3)) assert_=== none[List[Int]] }
  }

  // TODO[scalaz.Show,scalaz.EqualTo] Can't perform === on these because the there are missing traits.  Will return to do this after learning those traits!
  describe("List[T].toNel") {
    it("cannot convert an empty list to a 'non-empty' one") { pending } // { List().toNel assert_=== None }
    it("converts to a non-empty list")                      { pending } // { List(1,2,3).toNel assert_=== Some(nel(1, (2,3))) }
  }

  describe("List[T] <^>") {
    it("applies function to non-empty list") { pending } // { List(1,2,3) <^> ((l) => "List") assert_=== "List" }
    it("returns the zero if empty")          { pending } // { List()      <^> ((l) => "List") assert_=== "" }
  }
}
