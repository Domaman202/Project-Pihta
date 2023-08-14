package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.std.ast.NodeField
import ru.DmN.pht.base.unparsers.NodeUnparser

object NUField : NodeUnparser<NodeField>() {
    override fun unparse(unparser: Unparser, node: NodeField) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(" [")
            node.fields.forEach { append('[').append(it.first).append(' ').append(it.second)..append(']') }
            append("])")
        }
    }
}