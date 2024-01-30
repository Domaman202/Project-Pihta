package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeDef
import ru.DmN.pht.utils.nameWithGenerics
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser
import ru.DmN.siberia.utils.operation

object NUDef : INodeUnparser<NodeDef> {
    override fun unparse(node: NodeDef, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.operation).append(" [")
            if (node.isVariable) {
                node.variables.forEach { it ->
                    append('\n').append("\t".repeat(indent + 1)).append('[').append(it.type.nameWithGenerics).append(' ').append(it.name)
                    it.value?.let {
                        append(' ')
                        unparser.unparse(it, ctx, indent + 2)
                    }
                    append(']')
                }
            } else {
                node.variables.forEach {
                    append('\n').append("\t".repeat(indent + 1)).append('[').append(it.name).append(' ').append(it.type.nameWithGenerics).append(']')
                }
            }
            append("])")
        }
    }
}