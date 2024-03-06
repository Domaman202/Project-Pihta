package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeTypedGet
import ru.DmN.pht.utils.computeString
import ru.DmN.pht.utils.computeType
import ru.DmN.pht.utils.node.NodeTypes.TYPED_GET_
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRTypedGet : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.computeType(node.nodes[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeTypedGet =
        NodeTypedGet(node.info.withType(TYPED_GET_), processor.computeString(node.nodes[1], ctx), processor.computeType(node.nodes[0], ctx))
}