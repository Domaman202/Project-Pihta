package ru.DmN.pht.std.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRUnrollB : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? {
        val tmp = node.copy()
        var expr = processor.process(tmp.nodes[0], ctx, ValType.VALUE)!!
        for (i in 1 until tmp.nodes.size)
            expr = processor.process(tmp.nodes[tmp.nodes.size - i].apply { this as INodesList; this.nodes.add(0, expr) }, ctx, ValType.VALUE)!!
        return processor.calc(expr, ctx)
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        var expr = processor.process(node.nodes[0], ctx, ValType.VALUE)!!
        for (i in 1 until node.nodes.size)
            expr = processor.process(node.nodes[node.nodes.size - i].apply { this as INodesList; this.nodes.add(0, expr) }, ctx, ValType.VALUE)!!
        return expr
    }
}