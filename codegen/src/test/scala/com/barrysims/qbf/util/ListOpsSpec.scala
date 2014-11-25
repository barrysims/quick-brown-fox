package com.barrysims.qbf.util

import org.scalatest.{Matchers, FlatSpec}

/**
 * [[ListOps]] tests
 */
class ListOpsSpec extends FlatSpec with Matchers {
  
  import ListOps._
  
  "chunk list" should "split a list" in {

    val list = List("", "", "a", "b", "", "", "c", "", "d", "")

    val result = List(List("a", "b"), List("c"), List("d"))

    list.chunkList(_.isEmpty) should equal (result)
  }

}
