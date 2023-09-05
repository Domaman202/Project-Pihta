package ru.DmN.pht.std.base.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.base.ast.NodeGetOrName
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser

object NUGetOrName : NodeUnparser<NodeGetOrName>() {
    override fun unparse(node: NodeGetOrName, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(node.tkOperation.text).append(' ').append(node.name).append(')')
    }
}