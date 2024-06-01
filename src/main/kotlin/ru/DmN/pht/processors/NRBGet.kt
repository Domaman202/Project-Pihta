package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeBGet
import ru.DmN.pht.processor.ctx.BodyContext
import ru.DmN.pht.processor.ctx.NamedBodyContext
import ru.DmN.pht.processor.ctx.body
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.utils.node.NodeTypes.BGET_
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.exception.MessageException
import ru.DmN.siberia.utils.vtype.VirtualType

object NRBGet : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        find(node, processor, ctx).second.type

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeBGet? =
        if (valMode)
            find(node, processor, ctx).let { NodeBGet(node.info.withType(BGET_), it.first, it.second) }
        else null

    fun find(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): Pair<String, Variable> {
        val name = processor.computeString(node.nodes[0], ctx)
        val block = processor.computeString(node.nodes[1], ctx)
        var bctx: BodyContext? = ctx.body
        while (!(bctx is NamedBodyContext && bctx.name == block)) {
            if (bctx == null)
                throw MessageException(null, "Переменная '$name' в блоке '$block' не найдена!")
            bctx = bctx.parent
        }
        return Pair(block, bctx[name]!!)
    }
}