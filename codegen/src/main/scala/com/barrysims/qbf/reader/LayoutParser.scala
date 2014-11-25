package com.barrysims.qbf.reader

import com.barrysims.qbf.model._
import com.barrysims.qbf.util.ListOps._

/**
 * Parsers for Layouts
 */
trait LayoutParser[A] extends Parser[Row[A]] {

  protected val read = (mapping: Option[Mapping]) =>
    groupRowsByLayer andThen
    parseLayers andThen
    createLayers(mapping)

  private val ws = "[ \\t]+".r
  private lazy val groupRowsByLayer = (rows: List[String]) => rows.chunkList(_.trim.isEmpty)
  private lazy val parseLayers = (rowGroups: List[List[String]]) => rowGroups map parseLines(rowParser)
  private lazy val rowParser = comment | (repsep(parser, ws) ^^ {case r => Some(r)})
  private lazy val createLayers = (mapping: Option[Mapping]) => (rows: List[List[Row[A]]]) => rows.map(Layer(_, mapping))

  override val skipWhitespace = false

  protected val parser: Parser[A]

  implicit protected val default: A

}

/**
 * Specific layout parser for [[com.barrysims.qbf.model.KeyLayout]]s
 */
object KeyLayoutParser extends LayoutParser[String] {

  protected val parser = name

  implicit val default = "empty"

  /**
   * Parses a list of strings into a [[com.barrysims.qbf.model.KeyLayout]]
   *
   * Optionally takes a [[com.barrysims.qbf.model.Mapping]], applying it to the layout, resulting in
   * a key matrix layout that can be different from the physical layout.
   */
  val parseLayout: Option[Mapping] => List[String] => KeyLayout = mapping => read(mapping)

  /**
   * Parses a list of strings into a [[com.barrysims.qbf.model.KeyLayout]]
   */
  val parseKeyLayoutNoMapping: List[String] => KeyLayout = parseLayout(None)
}

/**
 * Specific layout parser for [[com.barrysims.qbf.model.Mapping]]s
 */
object MappingParser extends LayoutParser[Int] {

  protected val parser = number

  implicit val default = -1

  /**
   * Parses a list of strings into a [[com.barrysims.qbf.model.Mapping]]
   */
  val parseMapping: List[String] => Mapping = read(None) andThen Mapping.fromLayers
}
