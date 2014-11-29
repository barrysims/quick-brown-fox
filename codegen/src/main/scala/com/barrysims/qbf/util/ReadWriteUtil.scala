package com.barrysims.qbf.util

import scala.io.{Codec, Source}
import java.io.{PrintWriter, File}
import java.nio.charset.CodingErrorAction

/**
 * Utilities for reading and writing files, resources, strings etc
 */
object ReadWriteUtil {

  private implicit val codec = Codec("UTF-8")
  codec.onMalformedInput(CodingErrorAction.REPLACE)
  codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

  type ConfigReader[S] = S => List[String]
  type SourceWriter[D] = String => String => D

  /**
   * Reads a file into a list of strings
   */
  val readFile: ConfigReader[File] = (file: File) => stripBOM {
    Source.fromFile(file).getLines().toList
  }

  /**
   * reads a resource into a list of strings
   */
  val readResource: ConfigReader[String] = (path: String) => stripBOM {
    Source.fromURL(getClass.getResource(path)).getLines().toList
  }

  /*
   * writes a string to a file
   */
  def writeFile(path: String): SourceWriter[Unit] = (name: String) => (contents: String) => {
    val writer = new PrintWriter(new File(path + "/" + name))
    try writer.write(contents)
    finally writer.close()
  }

  /**
   * writes a string to a string, essentially a no-op writer
   */
  val writeString: SourceWriter[String] = (_: String) => (contents: String) => contents

  private def stripBOM(lines: List[String]) =
    lines match {
      case x :: xs if x.startsWith("\uFEFF") => x.substring(1) :: xs
      case xs => xs
    }
}
