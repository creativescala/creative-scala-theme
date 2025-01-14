# Creative Scala Theme

This is a demonstration of the Creative Scala theme. It is designed to show off the features of the theme, and serve as an example to speed up development.


## Installation

This theme is distributed as a sbt plugin. (You can use it without sbt, but you'll have to do a bit more work.) In your `project/plugins.sbt` add

```scala
addSbtPlugin("org.creativescala" % "creative-scala-theme" % "<version>")
```

replacing `<version>` with the most recent version. Then, in where you configure Laika in your `build.sbt`, add something like the following:

```scala
laikaTheme := CreativeScalaTheme.empty
  .addJs(laika.ast.Path.Root / "xterm.js")
  .addJs(laika.ast.Path.Root / "main.js")
  .addCss(laika.ast.Path.Root / "xterm.css")
  .build
```

replacing `addJs` etc. with appropriate values for your project. You can remove these calls if you don't have any custom configuration.


## Creating this Example

From sbt, simply use the `run` command:

```bash
sbt run
```

This will generate output in the `target` directory. Open `target/index.html` in a web browser to see the rendered output.


## Developing Content

You'll want to have Tailwind generating CSS whenever a change is made to `src/main/css/creative-scala.css`:

```bash
sbt css
```
