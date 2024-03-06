package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeClassOf
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUClassOf : INodeUnparser<NodeClassOf> {
    override fun unparse(node: NodeClassOf, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(node.operation).append(" ^").append(node.name).append(')')
    }
}