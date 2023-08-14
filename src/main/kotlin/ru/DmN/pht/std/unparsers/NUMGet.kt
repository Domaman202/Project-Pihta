package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.base.unparsers.NodeUnparser

object NUMGet : NodeUnparser<NodeFMGet>() {
    override fun unparse(unparser: Unparser, node: NodeFMGet) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ')
            unparser.unparse(node.instance)
            append('#').append(node.name).append(')')
        }
    }
}