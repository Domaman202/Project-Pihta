package ru.DmN.pht.processors

import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType

object NRValnSize : IComputableProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.INT

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
        nodeValue(node.info, computeInt(node, processor, ctx))

    override fun computeInt(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): Int =
        (node.nodes[0] as INodesList).nodes.size
}