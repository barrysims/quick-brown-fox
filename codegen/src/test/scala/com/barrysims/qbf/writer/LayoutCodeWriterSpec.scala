package com.barrysims.qbf.writer

import org.scalatest.{Matchers, FunSpec}
import com.barrysims.qbf.model._
import com.barrysims.qbf.writer.Writer.LayoutWritable

/**
 * [[LayoutWritable]] tests
 */
class LayoutCodeWriterSpec extends FunSpec with Matchers {

  implicit val default = ".."

  describe ("KeyLayoutWriter") {
    it ("should write a single layer layout") {
      val layout = Layout(
        Layer(
          Row("a", "b", "c"),
          Row("d", "e", "f"))
      )

      // todo: fail
      LayoutWritable.code(layout)
    }

    it ("should write a two layer layout") {
      val layout = Layout(
        Layer(
          Row("a", "b", "c"),
          Row("d", "e", "f")),
        Layer(
          Row("g", "h", "i"),
          Row("j", "k", "l"))
      )

      // todo: fail
      LayoutWritable.code(layout)
    }
  }
}
