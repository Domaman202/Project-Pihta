package ru.DmN.pht.std.ups

import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.pht.std.ast.NodeDef
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPDef : INodeUniversalProcessor<NodeDef, NodeDef> {
    override fun unparse(node: NodeDef, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(NUDefaultX.text(node.token)).append(" [")
            node.variables.forEachIndexed { i, it ->
                val offset = if (node.variables.size > 1) {
                    append('\n').append("\t".repeat(indent + 1))
                    1
                } else 0
                append('[').append(NUPValueA.unparseType(it.type.name)).append(' ').append(it.name)
                if (it.value != null) {
                    append('\n').append("\t".repeat(indent + 1 + offset))
                    unparser.unparse(it.value, ctx, indent + 1 + offset)
                    if (i + 1 < node.variables.size) {
                        append('\n').append("\t".repeat(indent + 1))
                    }
                }
                append(']')
            }
            append("])")
        }
    }
}