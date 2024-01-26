package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeInlBodyA
import ru.DmN.pht.std.utils.nameWithGenerics
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUInlBody : INodeUnparser<NodeInlBodyA> {
    override fun unparse(node: NodeInlBodyA, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(' ').append(node.type!!.nameWithGenerics)
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}