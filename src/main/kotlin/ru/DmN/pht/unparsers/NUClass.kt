package ru.DmN.pht.unparsers

import ru.DmN.pht.std.ast.NodeType
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUClass : INodeUnparser<NodeType> {
    override fun unparse(node: NodeType, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(' ').append(node.type.name).append(" [")
            node.type.parents.forEachIndexed { i, it ->
                if (i > 0)
                    append(' ')
                append('^').append(it.name)
            }
            append(']')
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}