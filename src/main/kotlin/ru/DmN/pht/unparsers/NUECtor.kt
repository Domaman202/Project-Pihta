package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeDefn
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUECtor : INodeUnparser<NodeDefn> {
    override fun unparse(node: NodeDefn, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            node.method.apply {
                append('(').append(node.operation).append(" [")
                argsn.asSequence().drop(2).forEachIndexed { i, it ->
                    append('[').append(it).append(" ^").append(argsc[i + 2].name).append(']')
                }
                append(']')
                NUDefault.unparseNodes(node, unparser, ctx, indent)
                append(')')
            }
        }
    }
}