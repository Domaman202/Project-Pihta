package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeNew
import ru.DmN.pht.utils.nameWithGenerics
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUNew : INodeUnparser<NodeNew> {
    override fun unparse(node: NodeNew, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(' ').append(node.type.nameWithGenerics)
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}