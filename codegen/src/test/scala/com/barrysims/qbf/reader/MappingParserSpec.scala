package com.barrysims.qbf.reader

import org.scalatest.{Matchers, FunSpec}
import com.barrysims.qbf.model.Mapping
import com.barrysims.qbf.util.ReadWriteUtil
import com.barrysims.qbf._

/**
 * [[MappingParser]] tests
 */
class MappingParserSpec extends FunSpec with Matchers {

  import MappingParser._

  describe("MappingReader") {

    it ("should parse a mapping") {

      val src = """1   0
                  |0   1
                  |
                  |0   1
                  |0   1"""

      val result = Mapping(
        Map(
          (0, 0) -> (0, 1),
          (0, 1) -> (1, 0),
          (1, 0) -> (0, 0),
          (1, 1) -> (1, 1)
        ), 1, 1)

      parseMapping(src) should equal(result)
    }

    it ("should parse a non-rectangular-mapping") {

      val src = """## Cols
                  |0   1   0
                  |2   1
                  |2
                  |
                  |## Rows
                  |0   0   1
                  |1   1
                  |0
                  |"""

      val result = Mapping(
        Map(
          (-1,-1) -> (2,2),
          (0,0) -> (0,0),
          (2,0) -> (0,2),
          (1,1) -> (1,1),
          (0,1) -> (2,0),
          (2,1) -> (0,1),
          (1,0) -> (1,0)),1,2)

      parseMapping(src) should equal(result)
    }

    it ("should parse a layouts.kinesis-mapping (without errors)") {
      ReadWriteUtil.readResource("/layouts/kinesis/mapping.txt")
    }
  }
}
