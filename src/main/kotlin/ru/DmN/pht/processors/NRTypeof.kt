package ru.DmN.pht.processors

import ru.DmN.pht.utils.node.nodeValueClass
import ru.DmN.pht.utils.node.nodeValueNil
import ru.DmN.pht.utils.ofPrimitive
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType

object NRTypeof : IStdNodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node {
        val type = processor.calc(node.nodes.first(), ctx)
        return if (type == null)
            nodeValueNil(node.info)
        else nodeValueClass(node.info, type.ofPrimitive())
    }

    override fun computeString(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): String? =
        computeType(node, processor, ctx)?.name

    override fun computeType(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes.first(), ctx)
}