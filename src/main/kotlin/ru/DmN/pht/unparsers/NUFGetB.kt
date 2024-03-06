package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeFGet
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUFGetB : INodeUnparser<NodeFGet> {
    override fun unparse(node: NodeFGet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation)
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append('\n').append("\t".repeat(indent + 1)).append(node.name).append(')')
        }
    }
}