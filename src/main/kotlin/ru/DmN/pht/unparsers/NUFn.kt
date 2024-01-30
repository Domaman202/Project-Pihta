package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeFn
import ru.DmN.pht.utils.nameWithGens
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUFn : INodeUnparser<NodeFn> {
    override fun unparse(node: NodeFn, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            if (node.processed == null) {
                append('(').append(node.operation).append(' ').append(node.source.type!!.nameWithGens).append(" [")
                node.source.refs.forEachIndexed { i, it ->
                    if (i > 0)
                        append(' ')
                    append(it.name)
                }
                append("][")
                node.source.args.forEachIndexed { i, it ->
                    if (i > 0)
                        append(' ')
                    append(it)
                }
                append(']')
            } else append("(progn")
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}