package ru.DmN.pht.std.macro.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser
import ru.DmN.pht.base.utils.indent
import ru.DmN.pht.std.macro.ast.NodeMacroArg

object NUMacroArg : NodeUnparser<NodeMacroArg>() {
    override fun unparse(node: NodeMacroArg, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(node.tkOperation.text).append(' ').append(node.name).append(')')
    }
}