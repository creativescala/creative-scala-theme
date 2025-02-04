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

import laika.ast._
import laika.parse.SourceCursor
import laika.parse.SourceFragment

final case class ExternalLink(
    target: ExternalTarget,
    text: String,
    options: Options = Options.empty
) extends Unresolved {

  val source: SourceFragment = SourceCursor.Generated

  type Self = ExternalLink

  def unresolvedMessage: String = s"Unresolved external link: $this"

  def runsIn(phase: RewritePhase): Boolean =
    phase.isInstanceOf[RewritePhase.Render]

  def render: Span =
    SpanLink(
      Seq(
        Text(text),
        InlineSVGIcon(
          "<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round' class='lucide lucide-square-arrow-out-up-right' > <path d='M21 13v6a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h6' /> <path d='m21 3-9 9' /> <path d='M15 3h6v6' /> </svg>"
        )
      ),
      target,
      options = options
    )

  def withOptions(options: Options): ExternalLink =
    this.copy(options = options)
}
object ExternalLink {

  /** Creates a simple text link to an external target. */
  def apply(
      url: String,
      text: String
  ): ExternalLink =
    new ExternalLink(ExternalTarget(url), text)
}
