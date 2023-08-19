package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser
import ru.DmN.pht.std.ast.NodeMacroDef

object NUMacroDef : NodeUnparser<NodeMacroDef>() {
    override fun unparse(unparser: Unparser, ctx: UnparsingContext, node: NodeMacroDef) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ').append(node.name)
            node.nodes.forEach { unparser.unparse(ctx, it) }
            append(')')
        }
    }
}