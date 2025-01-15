# CSS and Javascript

You can add your own CSS and Javascript files to your site using the `addCss` and `addJs` methods on `CreativeScalaTheme`. These takes instances of `laika.ast.Path`. Here's an example in use:

```scala
laikaTheme := CreativeScalaTheme.empty
  .addJs(laika.ast.Path.Root / "main.js")
  .addCss(laika.ast.Path.Root / "main.css")
  .build
```
