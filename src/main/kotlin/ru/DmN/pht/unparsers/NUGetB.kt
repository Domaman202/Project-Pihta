package ru.DmN.pht.unparsers

import ru.DmN.pht.std.ast.NodeGetA
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUGetB : INodeUnparser<NodeGetA> {
    override fun unparse(node: NodeGetA, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(' ').append(node.name)
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}