package creativescala

import sbt._
import sbt.Keys._

object CreativeScalaThemePlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    val creativeScalaTheme = CreativeScalaTheme
  }

  import autoImport._
  // override lazy val globalSettings: Seq[Setting[_]] = Seq(
  //   helloGreeting := "hi",
  // )

  // override lazy val projectSettings: Seq[Setting[_]] = Seq(
  //   hello := {
  //     val s = streams.value
  //     val g = helloGreeting.value
  //     s.log.info(g)
  //   }
  // )
}
