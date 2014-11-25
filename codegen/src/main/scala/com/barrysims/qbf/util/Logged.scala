package com.barrysims.qbf.util

import scala.collection.mutable.ListBuffer
import scala.language.implicitConversions

case class Logged(message: StringBuffer) {

  def log[T](r: => T): T = {
    Logged.messages += message
    try {
      val result = r
      Logged.messages.last.append(": ok")
      result
    }
    catch {
      case t: Throwable =>
        Logged.messages.last.append(": " + t.getMessage)
        throw t
    }
  }
}

/**
 * Control structure to assist building a log message
 *
 * Used in conjunction with the [[Logged.log]] method:
 * {{{
 * Logged {
 *   "Doing something" >> doSomething(..)
 *   "Doing something else" >> doSomethingElse(..)
 * }
 * }}}
 *
 * Will output:
 * {{{
 * Doing something: ok
 * Doing something else: ok
 * }}}
 *
 * Unless an exception is thrown by one of the operations,
 * in which case the following is output:
 * {{{
 * Doing something: ok
 * Doing something else: NullPointerException at line...
 * }}}
 */
object Logged  {

  def apply[T](r: => T): T = {
    Logged.clear()
    val result = r
    Logged.print()
    result
  }

  def add(message: String): Unit = {
    messages += new StringBuffer(message)
  }

  val messages = new ListBuffer[StringBuffer]()
  def clear(): Unit = { messages.clear() }
  def print(): String = messages.mkString("\n")
  implicit def stringToLog(message: String): Logged = new Logged(new StringBuffer(message))
}
