package com.barrysims

import org.scalatest.Tag
import scala.language.implicitConversions

/**
 * Some useful testing utilities
 */
package object qbf {

  object IntegrationTest extends Tag("com.barrysims.qbf.IntegrationTest")

  implicit def stringToList(s: String): List[String] = s.stripMargin.split("""\r?\n""").toList
}