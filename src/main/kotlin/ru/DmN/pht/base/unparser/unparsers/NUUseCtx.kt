package ru.DmN.pht.base.unparser.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ast.NodeUseCtx
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.Module

object NUUseCtx : NodeUnparser<NodeUseCtx>() {
    override fun unparse(unparser: Unparser, ctx: UnparsingContext, node: NodeUseCtx) {
        unparser.out.let {
            it.append('(').append(node.tkOperation.text)
            node.names.forEach { name ->
                it.append(' ').append(name)
                Module.MODULES[name]!!.inject(unparser, ctx)
            }
            it.append(' ')
            node.nodes.forEach { node -> unparser.unparse(ctx, node) }
            it.append(')')
        }
    }
}