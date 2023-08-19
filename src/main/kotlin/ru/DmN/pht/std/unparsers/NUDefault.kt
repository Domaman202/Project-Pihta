package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser

object NUDefault : NodeUnparser<NodeNodesList>() {
    override fun unparse(unparser: Unparser, ctx: UnparsingContext, node: NodeNodesList) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ')
            node.nodes.forEach { unparser.unparse(ctx, it) }
            append(')')
        }
    }
}