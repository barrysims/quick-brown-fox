package com.barrysims.qbf.reader

import org.scalatest.{Matchers, FunSpec}
import com.barrysims.qbf.model._
import com.barrysims.qbf._

/**
 * [[PinParser]] tests
 */
class PinParserSpec extends FunSpec with Matchers {

  import PinParser._

  describe("PinParser") {

    it("should parse a pin file") {

      val src = """row 1 2
                  |row 2 3
                  |
                  |row 3 exp0 a1
                  |row 4 exp0 b2
                  |
                  |col 1 4
                  |
                  |col 2 exp1 a3
                  |col 3 exp1 b4
                  |
                  |kill 5
                  |"""

      val result =
        List(
          TeensyPin("row", 1, 2),
          TeensyPin("row", 2, 3),
          ExpanderPin("row", 3, 1, 0),
          ExpanderPin("row", 4, 10, 0),
          TeensyPin("col", 1, 4),
          ExpanderPin("col", 2, 3, 1),
          ExpanderPin("col", 3, 12, 1),
          KillPin(5))

      parse(src) should equal(result)
    }
  }
}