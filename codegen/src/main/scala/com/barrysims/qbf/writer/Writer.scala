package com.barrysims.qbf.writer

import scala.annotation.implicitNotFound
import com.barrysims.qbf.model._
import twirl.api.TxtFormat

/**
 * Converts models into C++ code
 *
 * Uses a typeclass (for the hell of it) to declare a model writable
 * Actual conversion to strings made by the Twirl templating language
 */
object Writer {

  /**
   * @param model The datamodel to convert into C++ code
   * @param w The [[Writable]] typeclass implementation for the datamodel
   * @tparam M The model type
   * @return C++ code representing the datamodel
   */
  def write[M](model: M)(implicit w: Writable[M]): String = w.code(model)

  def format(code: TxtFormat.Appendable): String = code.toString().replaceAll("""(\r?\n){2,}""", "\n\n")

  /**
   * Typeclass for transforming models into C++ source code
   *
   * @tparam A The model type
   */
  @implicitNotFound("No member of type class CodeWriter in scope for ${A}")
  trait Writable[A] {
    def code(a: A): String
  }

  /**
   * CodeWriter typeclass implementation for List[[com.barrysims.qbf.model.Action]]
   */
  implicit object ActionWritable extends Writable[List[Action]] {
    def code(as: List[Action]): String = txt.actions.render _ andThen format apply as
  }

  /**
   * CodeWriter typeclass implementation for [[com.barrysims.qbf.model.KeyLayout]]
   */
  implicit object LayoutWritable extends Writable[KeyLayout] {
    def code(keyLayout: KeyLayout): String = txt.layout.render _ andThen format apply keyLayout
  }

  /**
   * CodeWriter typeclass implementation for [[com.barrysims.qbf.model.Pins]]
   */
  implicit object PinWritable extends Writable[Pins] {
    def code(pins: Pins): String = txt.pins.render _ andThen format apply pins
  }
}
