package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeFieldB
import ru.DmN.pht.utils.vtype.nameWithGenerics
import ru.DmN.siberia.unparser.Unparser
import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser

object NUFld : INodeUnparser<NodeFieldB> {
    override fun unparse(node: NodeFieldB, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append("(def-fld [")
            node.fields.forEach {
                append('\n').append("\t".repeat(indent + 1)).append('[').append(it.name).append(' ').append(it.type.nameWithGenerics).append(']')
            }
            append("])")
        }
    }
}