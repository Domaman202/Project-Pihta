package ru.DmN.pht.unparsers

import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.ast.NodeValue.Type.*
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.INodeUnparser

object NUValue : INodeUnparser<NodeValue> {
    override fun unparse(node: NodeValue, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            when (node.vtype) {
                STRING -> append('"').append(node.value).append('"')
                PRIMITIVE,
                CLASS,
                CLASS_WITH_GEN -> append('^').append(node.value)
                else -> append(node.value)
            }
        }
    }
}