package com.barrysims.qbf

import java.io.File
import com.barrysims.qbf.model._
import com.barrysims.qbf.writer.Writer
import com.barrysims.qbf.util.{ReadWriteUtil, Logged}
import Logged._
import ReadWriteUtil._
import com.barrysims.qbf.reader.{KeyLayoutParser, MappingParser, PinParser, ActionParser}

/**
 * Encapsulates code generation
 */
object CodeGenerator {

  /**
   * Reads configurations sources, converts them into C++ code, and writes them to files
   *
   * @param source The configuration sources
   * @param destination The path to the location that the C++ code should be written
   */
  def apply(source: SourceBundle[File], destination: String, inputAxis: String, activeMode: String): Unit =
    apply(source, readFile, writeFile(destination), inputAxis, activeMode)

  /**
   * Reads configurations sources, converts them into C++ code,
   * and writes them out using the injected reader and writer
   *
   * @param source A map of sources of type S
   * @param reader A reader capable of reading sources of type S
   * @param writer A writer capable of writing sources of type D
   * @tparam S The type of source (e.g. files, paths, or strings)
   * @tparam D The type of output (e.g. Unit when writing files, strings when testing)
   * @return A map of sources of type D
   */
  def apply[S, D](
      source: SourceBundle[S],
      reader: ConfigReader[S],
      writer: SourceWriter[D],
      inputAxis: String,
      activeMode: String): SourceBundle[D] = Logged {

    val configs: SourceBundle[List[String]] = source map {case(k, v) => (k, s"Reading $k" log reader(v))}
    generate(configs, inputAxis, activeMode) map {case(k, v) => (k, s"Writing $k" log { writer(s"$k.h")(v) })}
  }

  /**
   * Generates C++ code
   */
  def generate(source: SourceBundle[List[String]], inputAxis: String, activeMode: String): SourceBundle[String] = {

    val defaultActionSrc: List[String] = "Reading default actions file" log ReadWriteUtil.readResource("/layouts/default-keys.txt")

    val mapping = source.get("mapping") match {
      case Some(m) => "Parsing mapping" log Some(MappingParser.parseMapping(m))
      case None => "No mapping found" log None
    }

    val defaultActions = "Parsing default actions" log ActionParser.parse(defaultActionSrc)

    val actions = source.get("actions") match {
      case Some(as) => "Parsing custom actions" log ActionParser.parse(as)
      case None => "No custom actions found" log Nil
    }

    val pinList = "Parsing pins" log PinParser.parse(source("pins"))

    val pins = Pins(pinList, inputAxis, activeMode)

    val layout = "Parsing layout" log KeyLayoutParser.parseLayout(mapping)(source("layout"))

    Map[String, String](
      "Actions" -> Writer.write(defaultActions ++ actions),
      "Pins" -> Writer.write(pins),
      "Layout" -> Writer.write(layout)
    )
  }
}
