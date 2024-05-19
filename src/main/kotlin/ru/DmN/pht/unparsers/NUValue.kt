package ru.DmN.pht.unparsers

import ru.DmN.pht.ast.NodeValue
import ru.DmN.pht.ast.NodeValue.Type.*
import ru.DmN.siberia.unparser.Unparser

import ru.DmN.siberia.unparser.ctx.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser

object NUValue : INodeUnparser<NodeValue> {
    override fun unparse(node: NodeValue, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            when (node.vtype) {
                CHAR -> append('\'').append(node.value).append('\'')
                LONG -> {
                    append(node.value)
                    if (!(node.value.endsWith('l') || node.value.endsWith('L'))) {
                        append('l')
                    }
                }
                FLOAT -> {
                    append(node.value)
                    if (!node.value.contains('.'))
                        append(".0")
                    if (!(node.value.endsWith('f') || node.value.endsWith('F'))) {
                        append('f')
                    }
                }
                DOUBLE -> {
                    append(node.value)
                    if (!node.value.contains('.')) {
                        append(".0")
                    }
                }
                STRING -> append('"').append(node.value).append('"')
                PRIMITIVE, CLASS, CLASS_WITH_GEN -> append('^').append(node.value)
                else -> append(node.value)
            }
        }
    }
}