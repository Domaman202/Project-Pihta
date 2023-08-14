package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.std.ast.NodeFieldSet
import ru.DmN.pht.base.unparsers.NodeUnparser

object NUFieldSet : NodeUnparser<NodeFieldSet>() {
    override fun unparse(unparser: Unparser, node: NodeFieldSet) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ')
            unparser.unparse(node.instance)
            append("#${node.name} ")
            unparser.unparse(node.value!!)
            append(')')
        }
    }
}