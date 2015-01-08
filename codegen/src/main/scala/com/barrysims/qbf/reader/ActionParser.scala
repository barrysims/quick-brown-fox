package com.barrysims.qbf.reader

import com.barrysims.qbf.model._

/**
 * Parses an action config and turns it into a list of [[com.barrysims.qbf.model.Action]]s
 */
object ActionParser extends Parser[Action] {

  /**
   * Parses a list of strings into a list of [[com.barrysims.qbf.model.Action]]s
   */
  def parse: List[String] => List[Action] = parseLines(parser)

  private val parser: Parser[Option[Action]] =
    comment |
    "delay"~>name~number ^^ { case i~n => Some(DelayAction(i, n)) } |
    "holdAndRelease"~>name~name~name~number ^^ { case i~k1~k2~buffered => Some(HoldAndReleaseAction(i, k1, k2, buffered))} |
    "macro-noclear"~>name~rep(name) ^^ { case i~ks => Some(MacroAction(i, ks, 0)) } |
    "macro"~>name~rep(name) ^^ { case i~ks => Some(MacroAction(i, ks, 1)) } |
    "mode"~>name~number ^^ { case i~n => Some(LayerAction(i, n)) } |
    "modifier"~>name~value ^^ { case i~w => Some(ModifierAction(i, w)) } |
    "default"~>name~value ^^ { case i~w => Some(DefaultAction(i, w)) } |
    "string"~>name~string ^^ { case i~s => Some(StringAction(i, s)) } |
    "toggle"~>name~name ^^ { case i~s => Some(ToggleAction(i, s)) } |
    "shift"~>name~name~name ^^ { case i~s~ss => Some(ShiftAction(i, s, ss)) } |
    "click"~>name~number ^^ { case i~n => Some(ClickAction(i, n)) } |
    "swap"~>name~rep(number) ^^ { case i~ns => Some(SwapLayerAction(i, ns))} |
    "ctrl-super"~>name ^^ { case i => Some(SwapCtrlSuperAction(i))}
}
