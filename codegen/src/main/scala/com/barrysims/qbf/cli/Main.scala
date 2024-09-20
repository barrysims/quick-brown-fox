package com.barrysims.qbf.cli

import java.io.File

import com.barrysims.qbf.CodeGenerator
import com.barrysims.qbf.model._
import com.barrysims.qbf.util.Logged
import org.rogach.scallop._

/**
 * Command Line argument config
 * example usage: java -jar qbf-gen.jar -p -r "layout" "qbf"
 */
class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
  val pullup = opt(name = "pullup", short = 'p', descr = "Use pullup resistors", default = Some(false))
  val col = opt(name = "col", descr = "use column as input axis", short = 'c', default = Some(false))
  val row = opt(name = "row", descr = "use row as input axis", short = 'r', default = Some(false))
  val src = trailArg[String](name = "src", descr = "Location of source layout files")
  val dest = trailArg[String](name = "dest", descr = "Location of destination firmware code")
  requireOne(col, row)
}

/**
 * Main runnable CLI
 */
object Main {

  def main(args: Array[String]) {

    val conf = new Conf(args)
    val dir = new File(conf.src())
    if (dir == null) { println("Directory not found at " + conf.src()); return }
    val files = dir.listFiles()
    if (files == null || files.length == 0) { println("No source files found at " + conf.src()); return }
    val sourceBundle: SourceBundle[File] =
      files.collect { case f if f.getName != ".git" => f.getName.replaceFirst("[.][^.]+$", "") -> f }.toMap

    try {
      CodeGenerator(
        sourceBundle,
        conf.dest(),
        if (conf.col()) "COL" else "ROW",
        if (conf.pullup()) "INPUT_PULLUP" else "INPUT"
      )
    } catch {
      case t: Throwable =>
        Logged.add(t.getMessage)
        throw t
    } finally {
      println(Logged.print())
    }
  }
}
