package com.barrysims.qbf.reader

import org.scalatest.{Matchers, FunSpec}
import com.barrysims.qbf.model._
import scala.util.parsing.combinator.RegexParsers
import com.barrysims.qbf.util.ReadWriteUtil
import com.barrysims.qbf._

/**
 * [[KeyLayoutParser]] tests
 */
class KeyLayoutParserSpec extends FunSpec with Matchers with RegexParsers {

  import KeyLayoutParser._

  describe("Layout Parser") {

    it ("should parse a row") {

      val layout = "a b c"

      val result = Layout(
        Layer(
          Row("a".action, "b".action, "c".action)))

      parseKeyLayoutNoMapping(layout) should equal(result)
    }

    it ("should parse multiple rows") {

      val layout =
        """a    b    c
          |d    e    f"""

      val result = Layout(
        Layer(
          Row("a".action, "b".action, "c".action),
          Row("d".action, "e".action, "f".action)))

      parseKeyLayoutNoMapping(layout) should equal (result)
    }

    it ("should ignore comments") {

      val layout =
        """## A comment
          |a    b    c
          |d    e    f"""

      val result = Layout(
        Layer(
          Row("a".action, "b".action, "c".action),
          Row("d".action, "e".action, "f".action)))

      parseKeyLayoutNoMapping(layout) should equal(result)
    }

    it ("should parse multiple layers") {

      val layout =
        """a    b    c
          |d    e    f
          |
          |g    h    i
          |j    k    l"""

      val result = Layout(
        Layer(
          Row("a".action, "b".action, "c".action),
          Row("d".action, "e".action, "f".action)),
        Layer(
          Row("g".action, "h".action, "i".action),
          Row("j".action, "k".action, "l".action)))

      parseKeyLayoutNoMapping(layout) should equal(result)
    }

    it ("should parse non-rectangular layers") {

      val layout =
        """a   b   c
          |d   e
          |f"""

      val result = Layout(
        Layer(
          Row("a".action, "b".action, "c".action),
          Row("d".action, "e".action, "empty"),
          Row("f".action, "empty", "empty")))

      parseKeyLayoutNoMapping(layout) should equal(result)
    }

    it ("should read a layout with a non-rectangular mapping") {

      val mapping = Mapping(
        Map(
          (0,0) -> (0,0),
          (2,0) -> (0,2),
          (1,1) -> (1,1),
          (0,1) -> (2,0),
          (2,1) -> (0,1),
          (1,0) -> (1,0)),1,2)

      val layout =
        """a   b   c
          |d   e
          |f"""

      val result = List(
        Map(
          (0,0) -> "a".action,
          (0,1) -> "c".action,
          (1,0) -> "b".action,
          (1,1) -> "e".action,
          (2,0) -> "f".action,
          (2,1) -> "d".action))

      parseLayout(Some(mapping))(layout) should equal(result)
    }

    it ("should read a complex layout with mapping") {
      val mappingSrc = ReadWriteUtil.readResource("/layouts/kinesis/mapping.txt")
      val mapping = MappingParser.parseMapping(mappingSrc)

      val layoutSrc = ReadWriteUtil.readResource("/layouts/kinesis/layout.txt")

      parseLayout(Some(mapping))(layoutSrc)
    }
  }
}
