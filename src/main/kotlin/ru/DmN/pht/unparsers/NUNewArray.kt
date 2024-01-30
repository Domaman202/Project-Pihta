package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeNewArray
import ru.DmN.pht.utils.nameWithGenerics
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUNewArray : INodeUnparser<NodeNewArray> {
    override fun unparse(node: NodeNewArray, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(' ').append(node.type.nameWithGenerics).append(' ')
            unparser.unparse(node.size, ctx, indent + 1)
            append(')')
        }
    }
}