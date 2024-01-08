package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeTypedGet
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.computeType
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRTypedGet : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.computeType(node.nodes[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeTypedGet =
        NodeTypedGet(node.info.withType(NodeTypes.TYPED_GET_), processor.computeString(node.nodes[1], ctx), processor.computeType(node.nodes[0], ctx))
}