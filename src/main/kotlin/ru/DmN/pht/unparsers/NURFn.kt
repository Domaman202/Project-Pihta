package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeRFn
import ru.DmN.pht.utils.vtype.nameWithGens
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NURFn : INodeUnparser<NodeRFn> {
    override fun unparse(node: NodeRFn, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(' ').append(node.type?.nameWithGens ?: ".").append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.instance!!, ctx, indent + 1)
            append('\n').append("\t".repeat(indent + 1)).append(node.name).append(')')
        }
    }
}