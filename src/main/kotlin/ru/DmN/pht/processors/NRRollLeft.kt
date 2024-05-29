package ru.DmN.pht.processors

import ru.DmN.pht.processor.utils.compute
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRRollLeft : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        process(node.copy(), processor, ctx)?.let { processor.calc(it, ctx) }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        process(node, processor, ctx)

    private fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): Node? {
        val expr = processor.compute(node.nodes[0], ctx) as INodesList
        for (i in 1 until node.nodes.size)
            expr.nodes += processor.compute(node.nodes[i], ctx)
        return processor.process(expr, ctx, true)
    }
}