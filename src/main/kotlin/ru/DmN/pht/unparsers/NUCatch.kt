package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeCatch
import ru.DmN.pht.utils.nameWithGenerics
import ru.DmN.pht.utils.nameWithGens
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUCatch : INodeUnparser<NodeCatch> {
    override fun unparse(node: NodeCatch, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation)
            node.type?.let { append(' ').append(it.nameWithGens) }
            append(" [")
                node.catchers.forEach { it ->
                    append('\n').append("\t".repeat(indent + 1)).append('[').append(it.first).append(' ')
                        .append(it.second.nameWithGenerics)
                    it.third?.let {
                        append('\n').append("\t".repeat(indent + 2))
                        unparser.unparse(it, ctx, indent + 2)
                    }
                    append(']')
                }
            append(']')
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}