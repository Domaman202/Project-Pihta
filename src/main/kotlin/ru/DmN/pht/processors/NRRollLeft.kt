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
        processor.calc(process(node.copy(), processor, ctx), ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
        process(node, processor, ctx)

    private fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): Node {
        var expr = processor.compute(node.nodes[0], ctx)
        for (i in 1 until node.nodes.size) {
            expr as INodesList
            expr.nodes += node.nodes[i]
            expr = processor.process(expr, ctx, true)!!
        }
        return expr
    }
}