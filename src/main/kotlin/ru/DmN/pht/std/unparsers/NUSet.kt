package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.std.ast.NodeSet
import ru.DmN.pht.base.unparsers.NodeUnparser

object NUSet : NodeUnparser<NodeSet>() {
    override fun unparse(unparser: Unparser, node: NodeSet) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ').append(node.name).append(' ')
            unparser.unparse(node.value!!)
            append(')')
        }
    }
}