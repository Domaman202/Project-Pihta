package ru.DmN.pht.unparsers

import ru.DmN.pht.std.ast.NodeGet
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUGet : INodeUnparser<NodeGet> {
    override fun unparse(node: NodeGet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(' ').append(node.name).append(')')
        }
    }
}