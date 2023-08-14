package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparsers.NodeUnparser
import ru.DmN.pht.std.ast.NodeConstructorCall

object NUCtorCall : NodeUnparser<NodeConstructorCall>() {
    override fun unparse(unparser: Unparser, node: NodeConstructorCall) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ').append(if (node.supercall) "super" else "this").append(' ')
            node.nodes.forEach(unparser::unparse)
            append(')')
        }
    }
}