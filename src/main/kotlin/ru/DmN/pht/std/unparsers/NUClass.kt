package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.std.ast.NodeClass
import ru.DmN.pht.base.unparsers.NodeUnparser

object NUClass : NodeUnparser<NodeClass>() {
    override fun unparse(unparser: Unparser, node: NodeClass) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ').append(node.name)
            node.parents.forEach { append(" ^").append(it.replace('/', '.')) }
            node.nodes.forEach { append(' '); unparser.unparse(it) }
            append(')')
        }
    }
}