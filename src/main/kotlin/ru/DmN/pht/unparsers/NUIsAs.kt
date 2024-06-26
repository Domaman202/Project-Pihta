package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeIsAs
import ru.DmN.pht.utils.vtype.nameWithGenerics
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUIsAs : INodeUnparser<NodeIsAs> {
    override fun unparse(node: NodeIsAs, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(' ').append(node.type.nameWithGenerics)
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}