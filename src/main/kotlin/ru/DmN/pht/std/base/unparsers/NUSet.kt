package ru.DmN.pht.std.base.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.base.ast.NodeSet
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser

object NUSet : NodeUnparser<NodeSet>() {
    override fun unparse(unparser: Unparser, ctx: UnparsingContext, node: NodeSet) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ').append(node.name).append(' ')
            unparser.unparse(ctx, node.value!!)
            append(')')
        }
    }
}