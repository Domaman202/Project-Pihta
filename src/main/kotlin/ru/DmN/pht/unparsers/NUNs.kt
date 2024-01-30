package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeNs
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUNs : INodeUnparser<NodeNs> {
    override fun unparse(node: NodeNs, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(' ').append(node.namespace)
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}