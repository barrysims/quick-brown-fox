import AssemblyKeys._

import com.typesafe.sbt.SbtGit._

name := "Quick Brown Fox"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.3"

libraryDependencies ++= {
  Seq(
    "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
    "org.rogach" %% "scallop" % "0.9.5"
  )
}

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-target:jvm-1.7")

Seq(Twirl.settings: _*)

fork := true

proguardSettings

ProguardKeys.options in Proguard ++= Seq("-dontnote", "-dontwarn", "-ignorewarnings")

ProguardKeys.options in Proguard += ProguardOptions.keepMain("com.barrysims.qbf.cli.Main")

assemblySettings

outputPath in assembly := file(s"../dist/${version.value}/qbf-gen.jar")

val packageFirmware = TaskKey[File]("packageFirmware", "zips firmware for download")

packageFirmware := {
  val files = new java.io.File("../firmware/qbf").listFiles.toList
  val out = file(s"../dist/${version.value}/qbf.zip")
  IO.zip(files map (f => (f, f.getName)), out)
  out
}

val dist = TaskKey[Unit]("dist", "assembles codegen jar, zips firmware")

dist := {
  val cln = clean
  val cmp =  compile
  val codegen = assembly.value
  val firmware = packageFirmware.value
}

showCurrentGitBranch
