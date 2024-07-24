package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeMCall
import ru.DmN.siberia.unparser.Unparser

import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUMCall : INodeUnparser<NodeMCall> {
    override fun unparse(node: NodeMCall, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        if (node.special == null) {
            unparser.out.apply {
                append('(').append(node.operation).append('\n').append("\t".repeat(indent + 1))
                when (node.type) {
                    NodeMCall.Type.EXTEND -> {
                        unparser.unparse(node.nodes[0], ctx, indent + 1)
                        append('\n').append("\t".repeat(indent + 1)).append(node.method.name)
                        node.nodes.stream().skip(1).forEach { n ->
                            unparser.out.append('\n').append("\t".repeat(indent + 1))
                            unparser.unparse(n, ctx, indent + 1)
                        }
                        append(')')
                    }

                    else -> {
                        when (node.type) {
                            NodeMCall.Type.SUPER -> append("super")
                            else -> unparser.unparse(node.instance, ctx, indent + 1)
                        }
                        append('\n').append("\t".repeat(indent + 1)).append(node.method.name)
                        NUDefault.unparseNodes(node, unparser, ctx, indent)
                        append(')')
                    }
                }
            }
        } else unparser.unparse(node.special!!,  ctx, indent)
    }
}