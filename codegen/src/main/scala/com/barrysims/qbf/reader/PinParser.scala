package com.barrysims.qbf.reader

import com.barrysims.qbf.model._

/**
 * Parses a pin config and turns it into a list of [[com.barrysims.qbf.model.Pin]]s
 */
object PinParser extends Parser[Pin] {

  /**
   * Parses a list of strings into a list of [[com.barrysims.qbf.model.Pin]]s
   */
  val parse: List[String] => List[Pin] = parseLines(parser)

  private val axis = "row" | "col"
  private val chipAddress: Parser[Int] = """exp[\d]""".r ^^ { case s => s.last.asDigit }

  private lazy val expanderPin =
    """a[\d]""".r ^^ { case n => n.tail.toInt } |
    """b[\d]""".r ^^ { case n => n.tail.toInt + 8 }

  private lazy val parser: Parser[Option[Pin]] =
    comment |
    axis~number~chipAddress~expanderPin ^^ { case a~i~c~p => Some(ExpanderPin(a, i, p, c)) } |
    axis~number~number ^^ { case a~i~p => Some(TeensyPin(a, i, p)) } |
    "kill"~>number ^^ { case n => Some(KillPin(n)) }
}