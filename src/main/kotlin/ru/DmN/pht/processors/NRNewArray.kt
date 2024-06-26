package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeNewArray
import ru.DmN.pht.processor.utils.compute
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.utils.node.NodeTypes.NEW_ARR_
import ru.DmN.pht.utils.vtype.arrayType
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRNewArray : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.computeType(node.nodes[0], ctx).arrayType

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNewArray? =
        if (valMode)
            NodeNewArray(
                node.info.withType(NEW_ARR_),
                processor.computeType(node.nodes[0], ctx),
                processor.compute(node.nodes[1], ctx)
            )
        else null
}