package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeFieldB
import ru.DmN.pht.utils.nameWithGenerics
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser

object NUFld : INodeUnparser<NodeFieldB> {
    override fun unparse(node: NodeFieldB, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append("(def [")
            node.fields.forEach {
                append('\n').append("\t".repeat(indent + 1)).append('[').append(it.name).append(' ').append(it.type.nameWithGenerics).append(']')
            }
            append("])")
        }
    }
}