package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeFSet
import ru.DmN.siberia.unparser.Unparser

import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUFSetB : INodeUnparser<NodeFSet> {
    override fun unparse(node: NodeFSet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.nodes[0], ctx, indent + 1)
            append('\n').append("\t".repeat(indent + 1)).append(node.field.name)
            node.nodes.stream().skip(1).forEach { n ->
                unparser.out.append('\n').append("\t".repeat(indent + 1))
                unparser.unparse(n, ctx, indent + 1)
            }
            append(')')
        }
    }
}