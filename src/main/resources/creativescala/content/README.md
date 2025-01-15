# Creative Scala Theme

This is both a demonstration of the Creative Scala theme and documentation for it.


## Installation

This theme is distributed as a sbt plugin. (You can use it without sbt, but you'll have to do a bit more work.) In your `project/plugins.sbt` add

```scala
addSbtPlugin("org.creativescala" % "creative-scala-theme" % "<version>")
```

replacing `<version>` with the most recent version. Then, in where you configure Laika in your `build.sbt`, add something like the following:

```scala
laikaTheme := CreativeScalaTheme.empty
  .addJs(laika.ast.Path.Root / "main.js")
  .addCss(laika.ast.Path.Root / "main.css")
  .build
```

replacing `addJs` etc. with appropriate values for your project. You can remove these calls if you don't have any custom configuration.


## Development

Here are a few tips for development of this theme.


### Creating this Example

From sbt, simply use the `run` command from within sbt:

```bash
run
```

This will generate output in the `target` directory. Open `target/index.html` in a web browser to see the rendered output.


### Developing Content

When working on this theme you'll be spending most of your time working on the CSS in `src/main/css/creative-scala.css` and the template in `src/main/resources/creativescala/tempaltes/default.html.html`. The following sbt command will rebuild the CSS and then regenerate this site.

```bash
sbt css
```

The CSS uses [Tailwind CSS][tailwind]. You will need to have the `tailwindcss` executable installed as described in the Tailwind CSS documentation.

[tailwind]: https://tailwindcss.com/docs
