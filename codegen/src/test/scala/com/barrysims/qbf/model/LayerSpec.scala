package com.barrysims.qbf.model

import org.scalatest.{FunSpec, Matchers}

/**
 * [[Layer]] tests
 */
class LayerSpec extends FunSpec with Matchers {

  implicit val default = "?"

  describe("Layer") {

    it ("should initialise from parsed rows") {
      Layer(
        Row("a", "b"),
        Row("c", "d")) should equal {

      Map(
        (0, 0) -> "a",
        (1, 0) -> "b",
        (0, 1) -> "c",
        (1, 1) -> "d")
      }
    }

    it ("should initialise from non-rectangular parsed rows") {

      Layer(
        Row("a", "b"),
        Row("c")) should equal {

        Map(
          (0, 0) -> "a",
          (1, 0) -> "b",
          (0, 1) -> "c",
          (1, 1) -> "?")
      }
    }

    it ("should initialise from parsed rows with a mapping file") {

      val rows = List(
        Row("a", "b"),
        Row("c", "d")
      )

      val mapping = Mapping(
        Map(
          (0,0) -> (1,1),
          (1,0) -> (0,1),
          (0,1) -> (0,0),
          (1,1) -> (1,0)
        ), 1, 1)

      val result = Map(
        (0, 0) -> "d",
        (1, 0) -> "c",
        (0, 1) -> "a",
        (1, 1) -> "b")


      Layer(rows, Some(mapping)) should equal (result)
    }
  }
}
