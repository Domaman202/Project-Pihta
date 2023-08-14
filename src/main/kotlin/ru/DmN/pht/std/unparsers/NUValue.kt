package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.base.unparsers.NodeUnparser

object NUValue : NodeUnparser<NodeValue>() {
    override fun unparse(unparser: Unparser, node: NodeValue) {
        unparser.out.append('(').append(node.tkOperation.text).append(' ').append(
            when (node.vtype) {
                NodeValue.Type.NIL -> "nil"
                NodeValue.Type.BOOLEAN, NodeValue.Type.INT, NodeValue.Type.DOUBLE -> node.value
                NodeValue.Type.STRING -> "\"${node.value.replace("\"", "\\\"").replace("\n", "\\n").replace("\t", "\\t")}\""
                NodeValue.Type.PRIMITIVE, NodeValue.Type.CLASS -> "^${node.value}"
                NodeValue.Type.NAMING -> "#${node.value}"
            }
        ).append(')')
    }
}