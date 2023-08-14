package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.std.ast.NodeCast
import ru.DmN.pht.base.unparsers.NodeUnparser

object NUCast : NodeUnparser<NodeCast>() {
    override fun unparse(unparser: Unparser, node: NodeCast) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(" ^").append(node.to)
            unparser.unparse(node.value)
            append(')')
        }
    }
}