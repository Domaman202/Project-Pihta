package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.std.ast.NodeGet
import ru.DmN.pht.base.unparsers.NodeUnparser

object NUGet : NodeUnparser<NodeGet>() {
    override fun unparse(unparser: Unparser, node: NodeGet) {
        unparser.out.append('(').append(node.tkOperation.text).append(' ').append(node.name).append(')')
    }
}