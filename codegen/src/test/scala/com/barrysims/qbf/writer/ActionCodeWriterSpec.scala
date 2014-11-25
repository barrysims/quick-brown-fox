package com.barrysims.qbf.writer

import org.scalatest.{Matchers, FunSpec}
import com.barrysims.qbf.model.DefaultAction
import com.barrysims.qbf.writer.Writer.ActionWritable

/**
 * [[ActionWritable]] tests
 */
class ActionCodeWriterSpec extends FunSpec with Matchers {

  describe ("ActionWriter") {

    it ("should write actions") {

      val actions = List(
        DefaultAction("a", "KEY_A"),
        DefaultAction("b", "KEY_B"),
        DefaultAction("c", "KEY_C")
      )

      val result = ActionWritable.code(actions)

      result should include ("DefaultAction a = DefaultAction(KEY_A);")
      result should include ("DefaultAction b = DefaultAction(KEY_B);")
      result should include ("DefaultAction c = DefaultAction(KEY_C);")
    }
  }
}
