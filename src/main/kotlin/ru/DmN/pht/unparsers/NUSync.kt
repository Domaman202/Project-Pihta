package ru.DmN.pht.unparsers

import ru.DmN.pht.jvm.ast.NodeSync
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUSync : INodeUnparser<NodeSync> {
    override fun unparse(node: NodeSync, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.lock, ctx, indent + 1)
            append(')')
        }
    }
}