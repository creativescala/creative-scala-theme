package creativescala

import laika.theme.*
import laika.io.descriptor.ThemeDescriptor
import laika.io.model.InputTree
import cats.effect.Resource
import cats.syntax.all.*
import cats.effect.Async
import laika.io.model.InputTreeBuilder
import laika.ast.DefaultTemplatePath
import laika.ast.Path

object CreativeScalaTheme extends ThemeProvider {
  val cssPath = Path.Root / "css" / "creative-scala.css"
  val tocJsPath = Path.Root / "js" / "toc.js"
  val solutionJsPath = Path.Root / "js" / "solution.js"

  def build[F[_]: Async]: Resource[F, Theme[F]] =
    ThemeBuilder("creative-scala")
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
