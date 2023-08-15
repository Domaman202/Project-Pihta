package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparsers.NodeUnparser
import ru.DmN.pht.std.ast.NodeMacroArg

object NUMacroArg : NodeUnparser<NodeMacroArg>() {
    override fun unparse(unparser: Unparser, node: NodeMacroArg) {
        unparser.out.append('(').append(node.tkOperation.text).append(' ').append(node.name).append(')')
    }
}