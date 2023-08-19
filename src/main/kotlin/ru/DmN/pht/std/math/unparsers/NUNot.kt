package ru.DmN.pht.std.math.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.math.ast.NodeNot
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser

object NUNot : NodeUnparser<NodeNot>() {
    override fun unparse(unparser: Unparser, ctx: UnparsingContext, node: NodeNot) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ')
            unparser.unparse(ctx, node.value)
            append(')')
        }
    }
}