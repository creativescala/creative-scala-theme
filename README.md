# Creative Scala Theme

A Laika theme for Creative Scala.

![Current Version](https://img.shields.io/maven-central/v/org.creativescala/creative-scala-theme_2.12_1.0)

To use, add 

```scala
addSbtPlugin("org.creativescala" %% "creative-scala-theme" % VERSION)
```

to `project/plugins.sbt`, replacing `VERSION` with the version number above.

Then, in your `build.sbt`

```scala
import laika.ast.Path
import laika.helium.config.TextLink
// Configure Laika
Laika / sourceDirectories := Seq(
  mdocOut.value,
  "my" / "javascript", // the directory where JS is found
  "my" / "css" // the directory where CSS is found
),
laikaTheme := CreativeScalaTheme.empty
  .withHome(TextLink.interal(Path.Root / "README.md", "My Project Name"))
  .addJs(Path.Root / "main.js") // JS files to include
  .addCss(Path.Root / "main.css") // CSS files to include
  .build
```

Full documentation is in `src/main/resources/creativescala/content`. To build the documentation call `run` from sbt. The output will be in the `target` directory.
