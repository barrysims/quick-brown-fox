package com.barrysims.qbf.model

import org.scalatest.{Matchers, FunSpec}

/**
 * [[Mapping]] tests
 */
class MappingSpec extends FunSpec with Matchers {

  describe("Mapping") {

    implicit val default = -1

    it ("should initialise correctly from parsed layers") {

      val layers = List(
        Layer(
          Row(0, 1),
          Row(0, 1)
        ),
        Layer(
          Row(0, 1),
          Row(1, 0)
        )
      )

      val result = Mapping(
        Map(
          (0, 0) -> (0, 0),
          (1, 1) -> (1, 0),
          (1, 0) -> (1, 1),
          (0, 1) -> (0, 1)
        ), 1, 1)

      Mapping.fromLayers(layers) should equal (result)
    }
  }
}
