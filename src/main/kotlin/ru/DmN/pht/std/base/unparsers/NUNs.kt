package ru.DmN.pht.std.base.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.INodeUnparser
import ru.DmN.pht.base.unparser.unparsers.NUDefault
import ru.DmN.pht.std.base.ast.NodeNs

object NUNs : INodeUnparser<NodeNs> {
    override fun unparse(node: NodeNs, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ').append(node.namespace)
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}