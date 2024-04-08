package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeAGet
import ru.DmN.pht.processor.utils.processNodes
import ru.DmN.pht.utils.node.NodeTypes.AGET_
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRAGet : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[0], ctx)!!.componentType

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeAGet? =
        if (valMode) {
            val nodes = processor.processNodes(node, ctx, true)
            NodeAGet(node.info.withType(AGET_), nodes[0], nodes[1])
        } else null
}