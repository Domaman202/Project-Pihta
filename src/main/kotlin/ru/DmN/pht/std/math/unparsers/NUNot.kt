package ru.DmN.pht.std.math.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.math.ast.NodeNot
import ru.DmN.pht.base.unparser.unparsers.INodeUnparser

object NUNot : INodeUnparser<NodeNot> {
    override fun unparse(node: NodeNot, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ')
            unparser.unparse(node.value, ctx, indent)
            append(')')
        }
    }
}