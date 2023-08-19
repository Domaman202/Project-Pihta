package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.ast.NodeGetOrName
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser

object NUGetOrName : NodeUnparser<NodeGetOrName>() {
    override fun unparse(unparser: Unparser, ctx: UnparsingContext, node: NodeGetOrName) {
        unparser.out.append('(').append(node.tkOperation.text).append(' ').append(node.name).append(')')
    }
}