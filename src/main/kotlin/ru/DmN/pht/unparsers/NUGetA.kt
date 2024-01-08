package ru.DmN.pht.unparsers

import ru.DmN.pht.std.ast.NodeGetB
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUGetA : INodeUnparser<NodeGetB> {
    override fun unparse(node: NodeGetB, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(' ').append(node.name).append(')')
        }
    }
}