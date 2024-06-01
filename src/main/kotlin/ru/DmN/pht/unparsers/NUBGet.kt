package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeBGet
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUBGet : INodeUnparser<NodeBGet> {
    override fun unparse(node: NodeBGet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out
            .append('(').append(node.operation)
            .append(' ').append(node.variable.name)
            .append(' ').append(node.block)
            .append(')')
    }
}