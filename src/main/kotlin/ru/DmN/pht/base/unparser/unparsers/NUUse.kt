package ru.DmN.pht.base.unparser.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ast.NodeUse
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.utils.Module

object NUUse : NodeUnparser<NodeUse>() {
    override fun unparse(unparser: Unparser, ctx: UnparsingContext, node: NodeUse) {
        unparser.out.let {
            it.append('(').append(node.tkOperation.text)
            node.names.forEach { name ->
                it.append(' ').append(name)
                Module.MODULES[name]!!.inject(unparser, ctx)
            }
            it.append(')')
        }
    }
}