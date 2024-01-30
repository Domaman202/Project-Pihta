package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeIncDec
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUIncDec : INodeUnparser<NodeIncDec> {
    override fun unparse(node: NodeIncDec, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(node.operation).append(' ').append(node.name).append(')')
    }
}