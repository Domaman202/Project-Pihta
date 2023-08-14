package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.std.ast.NodeFunction
import ru.DmN.pht.base.unparsers.NodeUnparser

object NUFunction : NodeUnparser<NodeFunction>() {
    override fun unparse(unparser: Unparser, node: NodeFunction) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ').append(node.name).append(" ^").append(node.rettype).append(" [")
            node.args.forEach { append(' ').append(it.first).append(" ^").append(it.second.name) }
            append(" ]")
            if (node.nodes.isNotEmpty())
                append(' ')
            node.nodes.forEach { unparser.unparse(it) }
            append(')')
        }
    }
}