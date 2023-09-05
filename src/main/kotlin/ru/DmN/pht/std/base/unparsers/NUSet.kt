package ru.DmN.pht.std.base.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.base.ast.NodeSet
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser

object NUSet : NodeUnparser<NodeSet>() {
    override fun unparse(node: NodeSet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ').append(node.name).append(' ')
            unparser.unparse(node.value!!, ctx, indent)
            append(')')
        }
    }
}