package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeTGet
import ru.DmN.pht.utils.nameWithGenerics
import ru.DmN.siberia.unparser.Unparser

import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUTGet : INodeUnparser<NodeTGet> {
    override fun unparse(node: NodeTGet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(node.operation).append(' ').append(node.type.nameWithGenerics).append(' ').append(node.name).append(')')
    }
}