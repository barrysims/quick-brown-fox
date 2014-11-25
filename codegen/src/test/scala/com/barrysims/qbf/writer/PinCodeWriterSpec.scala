package com.barrysims.qbf.writer

import org.scalatest.{Matchers, FunSpec}
import com.barrysims.qbf.model._
import com.barrysims.qbf.writer.Writer.PinWritable

/**
 * [[PinWritable]] tests
 */
class PinCodeWriterSpec extends FunSpec with Matchers {

  val pins = Pins(List(
    TeensyPin("row", 0, 10),
    TeensyPin("col", 1, 11),
    ExpanderPin("row", 2, 20, 0),
    ExpanderPin("col", 3, 21, 1),
    KillPin(1)), "COL", "INPUT_PULLUP"
  )

  describe ("PinWriter") {

    // todo: fail
    it ("should write pin definitions") {
      PinWritable.code(pins)
    }

    // todo: fail
    it ("should write pin initialisation method") {
      PinWritable.code(pins)
    }

    // todo: fail
    it ("should write row and col arrays") {
      PinWritable.code(pins)
    }
  }
}
