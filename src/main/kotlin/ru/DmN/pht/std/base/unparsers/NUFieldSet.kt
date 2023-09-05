package ru.DmN.pht.std.base.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.base.ast.NodeFieldSet
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser

object NUFieldSet : NodeUnparser<NodeFieldSet>() {
    override fun unparse(node: NodeFieldSet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ')
            unparser.unparse(node.instance, ctx, indent)
            append("#${node.name} ")
            unparser.unparse(node.value!!, ctx, indent)
            append(')')
        }
    }
}