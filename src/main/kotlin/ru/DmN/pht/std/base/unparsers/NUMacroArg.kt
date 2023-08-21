package ru.DmN.pht.std.base.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser
import ru.DmN.pht.std.base.ast.NodeMacroArg

object NUMacroArg : NodeUnparser<NodeMacroArg>() {
    override fun unparse(unparser: Unparser, ctx: UnparsingContext, node: NodeMacroArg) {
        unparser.out.append('(').append(node.tkOperation.text).append(' ').append(node.name).append(')')
    }
}