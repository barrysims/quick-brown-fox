package com.barrysims.qbf.reader

import org.scalatest.{Matchers, FunSpec}
import com.barrysims.qbf.model._
import com.barrysims.qbf._

/**
 * [[ActionParser]] tests
 */
class ActionParserSpec extends FunSpec with Matchers {

  import ActionParser._

  describe ("action reader") {

    it ("should read an action") {

      val src = "default x y"

      parse(src) should have length 1
    }

    it ("should read a list of actions") {

      val src ="""default x y
                 |default a b"""

      parse(src) should have length 2
    }

    it ("should read a list of actions with blank lines") {

      val src ="""
                 |
                 |default x y
                 |
                 |
                 |default a b"""

      parse(src) should have length 2
    }

    it ("should fail to read an invalid action") {

      val src ="""
                 |
                 |default x y
                 |
                 |hello world
                 |default a b"""

      intercept[ParserException](parse(src))
    }

    it ("should read a list of actions correctly") {

      val src =
        """## This is a comment
          |
          |default foo bar
          |default = foo
          |default ❖ foo
          |
          |macro a b c d
        """

      val result = parse(src)

      result should have length 4
      result should contain (DefaultAction("foo".action, "bar"))
      result should contain (DefaultAction("=".action, "foo"))
      result should contain (DefaultAction("❖".action, "foo"))
      result should contain (MacroAction("a".action, List("b".action, "c".action, "d".action), 1))
    }
  }
}
