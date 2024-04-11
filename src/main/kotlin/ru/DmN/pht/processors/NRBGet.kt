package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeBGet
import ru.DmN.pht.processor.ctx.BodyContext
import ru.DmN.pht.processor.ctx.NamedBodyContext
import ru.DmN.pht.processor.ctx.body
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.utils.node.NodeTypes.BGET_
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.exception.MessageException
import ru.DmN.siberia.utils.vtype.VirtualType

object NRBGet : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        find(node, processor, ctx).type

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        if (valMode)
            NodeBGet(node.info.withType(BGET_), find(node, processor, ctx))
        else null

    private fun find(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): Variable {
        val name = processor.computeString(node.nodes[0], ctx)
        val block = processor.computeString(node.nodes[1], ctx)
        var bctx: BodyContext? = ctx.body
        while (!(bctx is NamedBodyContext && bctx.name == block)) {
            if (bctx == null)
                throw MessageException(null, "Переменная '$name' в блоке '$block' не найдена!")
            bctx = bctx.parent
        }
        return bctx[name]!!
    }
}