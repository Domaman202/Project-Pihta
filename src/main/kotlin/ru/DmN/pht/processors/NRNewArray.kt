package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeNewArray
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.utils.compute
import ru.DmN.pht.std.utils.computeType
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRNewArray : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.computeType(node.nodes[0], ctx).arrayType

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNewArray? =
        if (mode == ValType.VALUE)
            NodeNewArray(
                node.info.withType(NodeTypes.NEW_ARRAY_),
                processor.computeType(node.nodes[0], ctx),
                processor.compute(node.nodes[1], ctx)
            )
        else null
}