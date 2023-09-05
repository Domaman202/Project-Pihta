package ru.DmN.pht.base.unparser.unparsers

import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ast.NodeUseCtx
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.indent

object NUUseCtx : NodeUnparser<NodeUseCtx>() {
    override fun unparse(node: NodeUseCtx, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.let {
            it.append('(').append(node.tkOperation.text)
            node.names.forEach { name ->
                it.append(' ').append(name)
                Module.MODULES[name]!!.inject(unparser, ctx)
            }
            NUNodesList.unparseNodes(node, unparser, ctx, indent)
            it.append(')')
        }
    }
}