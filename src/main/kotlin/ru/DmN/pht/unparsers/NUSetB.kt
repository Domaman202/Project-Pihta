package ru.DmN.pht.unparsers

import ru.DmN.pht.std.ast.NodeSet
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUSetB : INodeUnparser<NodeSet> {
    override fun unparse(node: NodeSet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(' ').append(node.name).append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.value, ctx, indent + 1)
            append(')')
        }
    }
}