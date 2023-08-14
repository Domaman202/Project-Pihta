package ru.DmN.pht.base.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ast.NodeNodesList

object NUNodesList : NodeUnparser<NodeNodesList>() {
    override fun unparse(unparser: Unparser, node: NodeNodesList) {
        unparser.out.let {
            it.append('(').append(node.tkOperation.text).append(' ')
            node.nodes.forEach { n -> unparser.unparse(n) }
            it.append(')')
        }
    }
}