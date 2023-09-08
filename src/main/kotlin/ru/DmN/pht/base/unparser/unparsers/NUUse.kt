package ru.DmN.pht.base.unparser.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ast.NodeUse
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.Module

object NUUse : INodeUnparser<NodeUse> {
    override fun unparse(node: NodeUse, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
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