package com.barrysims.qbf.integration

import org.scalatest.{FlatSpec, Matchers}
import com.barrysims.qbf.util.ReadWriteUtil
import com.barrysims.qbf._

/**
 * [[CodeGenerator]] tests
 */
class CodeGenerationSpec extends FlatSpec with Matchers {

  "CodeGenerator" should "read and write a complex layout" taggedAs IntegrationTest in {

    val name = "kinesis"

    val sb = Map(
      "layout" -> s"/layouts/$name/layout.txt",
      "pins" -> s"/layouts/$name/pins.txt",
      "mapping" -> s"/layouts/$name/mapping.txt"
    )

    CodeGenerator(sb, ReadWriteUtil.readResource, ReadWriteUtil.writeString, "ROW", "INPUT")
  }

}
