# Creative Scala Theme

A Laika theme for Creative Scala.

![Current Version](https://img.shields.io/maven-central/v/org.creativescala/creative-scala-theme_3)

To use, add 

```scala
addSbtPlugin("org.creativescala" %% "creative-scala-theme" % VERSION)
```

to `project/plugins.sbt`, replacing `VERSION` with the version number above.

Then, in your `build.sbt`

```scala
import laika.ast.Path

// Configure Laika
Laika / sourceDirectories := Seq(
  mdocOut.value,
  "my" / "javascript", // the directory where JS is found
  "my" / "css" // the directory where CSS is found
),
laikaTheme := CreativeScalaTheme(
  Seq(Path.Root / "main.js"), // JS files to include
  Seq(Path.Root / "main.css") // CSS files to include
 ).build
```
