package com.barrysims.qbf.reader

import scala.util.parsing.combinator.JavaTokenParsers
import scala.language.implicitConversions

/**
 * Parser supertype
 *
 * Scala's parser-combinators are trait-based,
 * which introduces quite a lot of object-orientation into the code.
 *
 * This is at odds with the more functional approach taken elsewhere.
 */
trait Parser[A] extends JavaTokenParsers {

  implicit def stringToActionString(s: String): ActionString = new ActionString(s)

  override val whiteSpace = "[ \\t]+".r

  /**
   * General line parser
   *
   * Parses a list of strings into a list of type A.
   * Parsing happens line by line in order to identify
   * failing line numbers.
   */
  val parseLines: Parser[Option[A]] => List[String] => List[A] = (parser: Parser[Option[A]]) => (source: List[String]) =>
    source.zipWithIndex.
      filter {case(s, i) => !s.stripLineEnd.trim.isEmpty}.
      map {case(s, i) => (parse(parser, s.trim), i)}.
      map {case(s, i) => s.getOrElse {
        throw ParserException(
          s"Problem reading line ${i + 1}: $s"
        )
      }}.flatten

  protected val comment: Parser[Option[A]] = "^##.+".r ^^ { case co => None }
  protected val value: Parser[String] = "[^\\s]+".r
  protected val name: Parser[String] = value ^^ { case s => s.action }
  protected val number = wholeNumber ^^ { case n => n.toInt }

}

/**
 * Encodes a string so that it can be used as a C++ identifier
 */
class ActionString(s: String) {
  def action: String = s"action_$hash"
  private val charToHash = (c: Char) =>
    if (48 <= c && c <= 57) (c.toInt - 48).toString
    else if (97 <= c && c <= 122 ||
        65 <= c && c <= 90) c.toString
    else c.toInt
  private val hash = s.toCharArray.map(charToHash).mkString("_")
}

/**
 * Parser exception
 */
case class ParserException(msg: String) extends Exception(msg)
