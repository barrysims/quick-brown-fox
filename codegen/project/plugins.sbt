addSbtPlugin("io.spray" % "sbt-twirl" % "0.7.0")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.10.2")

addSbtPlugin("com.typesafe.sbt" % "sbt-proguard" % "0.2.2")

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.4")

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"

resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"
