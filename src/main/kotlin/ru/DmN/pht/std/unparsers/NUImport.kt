package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.std.ast.NodeImport
import ru.DmN.pht.base.unparsers.NodeUnparser

object NUImport : NodeUnparser<NodeImport>() {
    override fun unparse(unparser: Unparser, node: NodeImport) {
        unparser.out.append('(').append(node.tkOperation.text).append(' ').append(node.import).append(')')
    }
}