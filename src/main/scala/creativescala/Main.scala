/*
 * Copyright 2024 Creative Scala
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package creativescala

import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp
import laika.api._
import laika.ast.Path
import laika.config.ApiLinks
import laika.config.LinkConfig
import laika.config.SourceLinks
import laika.config.SyntaxHighlighting
import laika.format._
import laika.helium.config.TextLink
import laika.io.model._
import laika.io.syntax._

object Main extends IOApp {
  def run(args: List[String]) = {
    val transformerIO = Transformer
      .from(Markdown)
      .to(HTML)
      .using(Markdown.GitHubFlavor, SyntaxHighlighting)
      .withConfigValue(
        LinkConfig.empty
          .addApiLinks(
            ApiLinks(
              "https://javadoc.io/doc/org.creativescala/creative-scala-theme_2.12/latest/"
            )
          )
          .addSourceLinks(
            SourceLinks(
              "https://github.com/creativescala/creative-scala-theme",
              suffix = "scala"
            )
          )
      )
      .parallel[IO]
      .withTheme(
        CreativeScalaTheme.empty
          .withHome(
            TextLink.internal(Path.Root / "README.md", "Creative Scala Theme")
          )
          .withCommunity(
            TextLink.external("https://discord.gg/rRhcFbJxVG", "Community")
          )
          .withSource(
            TextLink.external(
              "https://github.com/creativescala/creative-scala-theme",
              "Source"
            )
          )
          .build
      )
      .build

    val inputs = InputTree[IO]
      .addDirectory("src/main/resources/creativescala/content")

    transformerIO.use { transformer =>
      transformer
        .fromInput(inputs)
        .toDirectory("target")
        .transform
    }
  }.as(ExitCode.Success)
}
