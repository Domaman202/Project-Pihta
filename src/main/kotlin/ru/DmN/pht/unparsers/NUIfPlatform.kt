package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeIfPlatform
import ru.DmN.siberia.unparser.Unparser

import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.operation

object NUIfPlatform : INodeUnparser<NodeIfPlatform> {
    override fun unparse(node: NodeIfPlatform, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation)
            node.platforms.forEach { append(' ').append(it) }
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}