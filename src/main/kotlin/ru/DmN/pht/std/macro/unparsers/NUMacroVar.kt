package ru.DmN.pht.std.macro.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser
import ru.DmN.pht.std.macro.ast.NodeMacroVar

object NUMacroVar : NodeUnparser<NodeMacroVar>() {
    override fun unparse(unparser: Unparser, ctx: UnparsingContext, node: NodeMacroVar) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ').append(node.name).append(' ')
            node.nodes.forEach { unparser.unparse(ctx, it) }
            append(')')
        }
    }
}