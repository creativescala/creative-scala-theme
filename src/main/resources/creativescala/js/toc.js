// Reformats table of contents so it can be styled as we desire
function tocPatch() {
  // All the TOCs (there should be two, for mobile and desktop.)
  const nodes = document.querySelectorAll("ul.nav-list");

  // type Hunk = {
  //   type: Type,
  //   name: String,
  //   href: String,
  //   active: Boolean,
  //   children: Array[Hunk]
  // }
  // type Type = "Part" | "Chapter" | "Section"

  // A Hunk with no children is a Chapter
  //
  // A Hunk with one level of children is a Chapter and those children are Sections
  //
  // A Hunk with two levels of children is a Part, which children who are
  // Chapters, and those children have Section children.
  //
  // The input is a flat list of Nodes. We use clases on the Nodes to determine
  // how they are organized into Hunks.
  //

  // (String, String, Boolean, Array[Hunk]) => Hunk
  function part(name, href, active, children = []) {
    return {
      type: "Part",
      name: name,
      href: href,
      active: active,
      children: children,
    };
  }

  // (String, String, Boolean, Array[Hunk]) => Hunk
  function chapter(name, href, active, children = []) {
    return {
      type: "Chapter",
      name: name,
      href: href,
      active: active,
      children: children,
    };
  }

  // (String, String, Boolean, Array[Hunk]) => Hunk
  function section(name, href, active, children) {
    return {
      type: "Section",
      name: name,
      href: href,
      active: active,
      children: children,
    };
  }

  // Element => String
  function itemName(item) {
    return item.children[0].textContent;
  }

  // Element => String
  function itemHref(item) {
    return item.children[0].getAttribute("href");
  }

  // Element => Boolean
  function itemIsActive(item) {
    return item.classList.contains("active");
  }

  // Array[Element] => Array[Hunk]
  function itemsToHunks(items) {
    let hunks = [];

    while (items.length > 0) {
      let item = items.shift();
      let hunk = itemToHunk(item, items);

      hunks.push(hunk);
    }

    return hunks;
  }

  // (Element, Array[Element]) => Hunk
  //
  // Rest is mutated
  function itemToHunk(item, rest) {
    let hunk = {};

    if (item.classList.contains("nav-leaf")) {
      // It's a stand alone hunk with no children
      hunk = chapter(itemName(item), itemHref(item), itemIsActive(item));
    } else {
      let [type, children] = itemChildren(2, rest);
      switch (type) {
        case "Part":
          hunk = part(
            itemName(item),
            itemHref(item),
            itemIsActive(item),
            children,
          );
        case "Chapter":
          hunk = chapter(
            itemName(item),
            itemHref(item),
            itemIsActive(item),
            children,
          );
      }
    }

    return hunk;
  }

  function childType(type) {
    switch (type) {
      case "Part":
        return "Chapter";
      case "Chapter":
        return "Section";
    }
  }

  // (Int, Array[Element]) => [Type, Array[Hunk]]
  //
  // Rest is mutated
  function itemChildren(level, rest) {
    let children = [];
    let type = "Chapter";

    while (typeof (item = rest.shift()) !== "undefined") {
      if (item.classList.contains("level1")) {
        // This item is a top-level hunk so we've finished finding the children.
        children.forEach((c) => (c.type = childType(type)));
        rest.unshift(item);

        return [type, children];
      } else if (item.classList.contains("level2")) {
        if (level > 2) {
          // We've finished finding the children for the prior element
          children.forEach((c) => (c.type = "Section"));
          rest.unshift(item);

          return [type, children];
        } else {
          let hunk = {
            name: itemName(item),
            href: itemHref(item),
            isActive: itemIsActive(item),
            children: [],
          };

          children.push(hunk);
          continue;
        }
      } else if (item.classList.contains("level3")) {
        if (level < 3) {
          type = "Part";
          rest.unshift(item);

          return itemChildren(3, rest);
        } else {
          let hunk = section(
            itemName(item),
            itemHref(item),
            itemIsActive(item),
          );

          children.push(hunk);
          continue;
        }
      } else {
        throw new Error("Unhandled item", item);
      }
    }
  }

  // Array[Hunk] => Array[Element]
  function hunksToToc(hunks) {
    return hunks.map((hunk) => hunkToTocItem(hunk));
  }

  function hunkToTocItem(hunk) {
    let item = document.createElement("li");

    let link = document.createElement("a");
    link.setAttribute("href", hunk.href);
    link.appendChild(document.createTextNode(hunk.name));

    if (hunk.children.length == 0) {
      item.classList.add("nav-leaf");
      item.appendChild(link);
    } else {
      item.classList.add("nav-node");

      let details = document.createElement("details");
      let summary = document.createElement("summary");
      let h5 = document.createElement("h5");
      let ul = document.createElement("ul");
      let children = hunk.children.map((hunk) => hunkToTocItem(hunk));

      h5.append(link);
      summary.append(h5);
      ul.append(...children);
      details.append(summary, ul);

      if (hunk.active) {
        details.setAttribute("open", "true");
      }

      item.appendChild(details);
    }

    switch (hunk.type) {
      case "Part":
        item.classList.add("part");
        break;
      case "Chapter":
        item.classList.add("chapter");
        break;
      case "Section":
        item.classList.add("section");
        break;
    }

    return item;
  }

  function makeToc() {
    nodes.forEach((node) => {
      const hunks = itemsToHunks(Array.from(node.children));
      const toc = hunksToToc(hunks);

      node.replaceChildren(...toc);
    });
  }

  makeToc();
}
// Run it when the page loads
addEventListener("load", () => tocPatch());
