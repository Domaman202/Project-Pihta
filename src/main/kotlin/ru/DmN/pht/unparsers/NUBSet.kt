package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeBSet
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUBSet : INodeUnparser<NodeBSet> {
    override fun unparse(node: NodeBSet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation)
                .append(' ').append(node.variable.name)
                .append(' ').append(node.block)
                .append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.value, ctx, indent + 1)
            append(')')
        }
    }
}