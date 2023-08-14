package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparsers.NodeUnparser
import ru.DmN.pht.std.ast.NodeMethodCall

object NUMethodCall : NodeUnparser<NodeMethodCall>() {
    override fun unparse(unparser: Unparser, node: NodeMethodCall) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(" #").append(node.name).append(' ')
            node.nodes.forEach(unparser::unparse)
            append(')')
        }
    }
}