package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.std.ast.NodeFor
import ru.DmN.pht.base.unparsers.NodeUnparser

object NUFor : NodeUnparser<NodeFor>() {
    override fun unparse(unparser: Unparser, node: NodeFor) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ').append(node.name).append(' ')
            node.nodes.forEach { unparser.unparse(it) }
            append(')')
        }
    }
}