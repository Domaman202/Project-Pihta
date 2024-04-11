package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeInner
import ru.DmN.siberia.unparser.Unparser

import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUInner : INodeUnparser<NodeInner> {
    override fun unparse(node: NodeInner, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(node.operation).append(" [").append(node.field).append(" ^").append(node.type).append("])")
    }
}