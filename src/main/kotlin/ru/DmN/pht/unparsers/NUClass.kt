package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeType
import ru.DmN.pht.utils.nameWithGenerics
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUClass : INodeUnparser<NodeType> {
    override fun unparse(node: NodeType, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(" [")
            node.type.generics.entries.forEach { append('[').append(it.key).append(' ').append(it.value.nameWithGenerics).append(']') }
            append("] ").append(node.type.name).append(" [")
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