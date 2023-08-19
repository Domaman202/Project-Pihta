package ru.DmN.pht.std.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.ast.NodeFieldSet
import ru.DmN.pht.base.unparser.unparsers.NodeUnparser

object NUFieldSet : NodeUnparser<NodeFieldSet>() {
    override fun unparse(unparser: Unparser, ctx: UnparsingContext, node: NodeFieldSet) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ')
            unparser.unparse(ctx, node.instance)
            append("#${node.name} ")
            unparser.unparse(ctx, node.value!!)
            append(')')
        }
    }
}