package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.unparsers.NodeUnparser

object NUIf : NodeUnparser<NodeNodesList>() {
    override fun unparse(unparser: Unparser, node: NodeNodesList) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ')
            unparser.unparse(node.nodes[0])
            append(" then ")
            unparser.unparse(node.nodes[1])
            if (node.nodes.size == 3) {
                append(" else ")
                unparser.unparse(node.nodes[2])
            }
            append(')')
        }
    }
}