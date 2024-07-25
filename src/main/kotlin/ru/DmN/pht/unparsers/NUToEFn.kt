package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeToEFn
import ru.DmN.pht.utils.vtype.nameWithGens
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUToEFn : INodeUnparser<NodeToEFn> {
    override fun unparse(node: NodeToEFn, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append("(").append(node.operation).append(' ').append(node.type.nameWithGens).append(' ').append(node.name).append(" [")
            node.args.forEachIndexed { i, it -> if (i > 0) append(' '); append(it.nameWithGens) }
            append("])")
        }
    }
}