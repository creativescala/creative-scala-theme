# Top Navigation

You can customize the links in the top navigation bar to link to your own community, API, and so on. To do this you call methods on an instance of `CreativeSCalaTheme`. This will usually be in your `build.sbt` where you'll have a line something like

```scala
laikaTheme := CreativeScalaTheme.empty
  .build
```

The instance of `CreativeScalaTheme`, created by the code `CreativeScalaTheme.empty`, has the following methods:

- `withHome`, which allows you to set the link in the center of the top bar (which is "Creative Scala Theme" above). It is supposed to link to the home page of your documentation site and defaults to the root of your documentation.
- `withCommunity`, which allows you to set the link to your community (e.g. a group chat service like Discord).
- `withApi`, which allows you to link to your API documentation. For most Scala projects this will be `javadoc.io`.
- `withSource`, which allows you to specify the link to your source code, often on Github.

All of these methods take a value of `SingleTargetLink`, which is taken from Laika's Helium theme. You'll need the import below to use it.

```scala
import laika.helium.config.SingleTargetLink
```

This allows you specify links in a variety of formats, including images and icons. Below are examples of creating a text link to another page in your Laika documentation, and a text link to an external site.

```scala
TextLink.internal(Path.Root / "README.md", "Home"),
TextLink.external("https://discord.gg", "Community"),
```
