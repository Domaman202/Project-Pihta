package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparsers.NodeUnparser
import ru.DmN.pht.std.ast.NodeExFunction

object NUExFunction : NodeUnparser<NodeExFunction>() {
    override fun unparse(unparser: Unparser, node: NodeExFunction) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(" ^").append(node.clazz).append(' ').append(node.name).append(" ^").append(node.rettype).append(" [")
            node.args.forEach { append(' ').append(it.first).append(" ^").append(it.second.name) }
            append(" ]")
            if (node.nodes.isNotEmpty())
                append(' ')
            node.nodes.forEach { unparser.unparse(it) }
            append(')')
        }
    }
}