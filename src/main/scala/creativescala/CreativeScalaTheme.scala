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

import cats.effect.Async
import cats.effect.Resource
import laika.ast.DefaultTemplatePath
import laika.ast.Path
import laika.io.model.InputTree
import laika.theme._
import sbt._

final case class CreativeScalaTheme(jsPaths: Seq[Path], cssPaths: Seq[Path]) {
  def addJs(path: Path): CreativeScalaTheme =
    this.copy(jsPaths = path +: jsPaths)

  def addCss(path: Path): CreativeScalaTheme =
    this.copy(cssPaths = path +: cssPaths)

  val cssPath = Path.Root / "css" / "creative-scala.css"
  val tocJsPath = Path.Root / "js" / "toc.js"
  val solutionJsPath = Path.Root / "js" / "solution.js"

  def build: ThemeProvider =
    new ThemeProvider {
      val directives = new CreativeScalaDirectives(
        tocJsPath +: solutionJsPath +: jsPaths,
        cssPath +: cssPaths
      )

      def build[F[_]: Async]: Resource[F, Theme[F]] =
        ThemeBuilder("creative-scala")
          .addExtensions(directives)
          .addInputs(
            InputTree[F]
              .addClassLoaderResource(
                "creativescala/templates/default.template.html",
                DefaultTemplatePath.forHTML
              )
              .addClassLoaderResource(
                "creativescala/css/creative-scala.css",
                cssPath
              )
              .addClassLoaderResource(
                "creativescala/js/toc.js",
                tocJsPath
              )
              .addClassLoaderResource(
                "creativescala/js/solution.js",
                solutionJsPath
              )
          )
          .build
    }
}
object CreativeScalaTheme {
  val empty: CreativeScalaTheme = CreativeScalaTheme(Seq.empty, Seq.empty)
}
