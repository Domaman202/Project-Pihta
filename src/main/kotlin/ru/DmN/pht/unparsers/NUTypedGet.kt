package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeTypedGet
import ru.DmN.pht.utils.nameWithGenerics
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUTypedGet : INodeUnparser<NodeTypedGet> {
    override fun unparse(node: NodeTypedGet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(node.operation).append(' ').append(node.type.nameWithGenerics).append(' ').append(node.name).append(')')
    }
}