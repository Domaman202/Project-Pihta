package ru.DmN.pht.processors

import ru.DmN.pht.node.NodeTypes.IF_
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRIf : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        if (node.nodes.size == 3)
            processor.calc(node.nodes[1], ctx)
        else null

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val nodes = mutableListOf(processor.process(node.nodes[0], ctx, ValType.VALUE)!!)
        for (i in 1 until node.nodes.size)
            nodes += processor.process(node.nodes[i], ctx, mode)!!
        return NodeNodesList(node.info.withType(IF_), nodes)
    }
}