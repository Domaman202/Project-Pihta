package ru.DmN.pht.std.base.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser
import ru.DmN.pht.std.base.ast.NodeValue
import ru.DmN.pht.std.base.ast.NodeValue.Type.*

object NUValue : NodeUnparser<NodeValue>() {
    override fun unparse(node: NodeValue, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(node.tkOperation.text).append(' ').append(
            when (node.vtype) {
                NIL -> "nil"
                BOOLEAN, CHAR, INT, LONG, FLOAT, DOUBLE -> node.value
                STRING -> "\"${node.value.replace("\"", "\\\"").replace("\n", "\\n").replace("\t", "\\t")}\""
                PRIMITIVE, CLASS -> "^${node.value}"
                NAMING -> "#${node.value}"
            }
        ).append(')')
    }
}