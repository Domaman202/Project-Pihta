package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeFieldSet
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUFSetA : INodeUnparser<NodeFieldSet> {
    override fun unparse(node: NodeFieldSet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.instance, ctx, indent + 1)
            append('\n').append("\t".repeat(indent + 1)).append(node.name)
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}