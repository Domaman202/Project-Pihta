package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparsers.NodeUnparser
import ru.DmN.pht.std.ast.NodeGeneric

object NUGeneric : NodeUnparser<NodeGeneric>() {
    override fun unparse(unparser: Unparser, node: NodeGeneric) {
        unparser.out.append('(').append(node.tkOperation).append(' ').append(node.name).append(' ').append(node.type).append(')')
    }
}