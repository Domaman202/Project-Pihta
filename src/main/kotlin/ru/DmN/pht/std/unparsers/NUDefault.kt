package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.unparsers.NodeUnparser

object NUDefault : NodeUnparser<NodeNodesList>() {
    override fun unparse(unparser: Unparser, node: NodeNodesList) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ')
            node.nodes.forEach { unparser.unparse(it) }
            append(')')
        }
    }
}