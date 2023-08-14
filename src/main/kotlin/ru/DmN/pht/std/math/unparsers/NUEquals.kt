package ru.DmN.pht.std.math.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.std.math.ast.NodeEquals
import ru.DmN.pht.base.unparsers.NodeUnparser

object NUEquals : NodeUnparser<NodeEquals>() {
    override fun unparse(unparser: Unparser, node: NodeEquals) {
        unparser.out.apply {
            append('(').append(when (node.operation) {
                NodeEquals.Operation.EQ -> "="
                NodeEquals.Operation.NE -> "!="
                NodeEquals.Operation.LT -> "<"
                NodeEquals.Operation.LE -> "<"
                NodeEquals.Operation.GT -> "<"
                NodeEquals.Operation.GE -> "<"
            }).append(' ')
            node.nodes.forEach { unparser.unparse(it) }
            append(')')
        }
    }
}