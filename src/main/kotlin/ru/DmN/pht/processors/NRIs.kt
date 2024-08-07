package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeIsAs
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.utils.node.NodeTypes.IS_
import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.isPrimitive
import ru.DmN.siberia.utils.vtype.VirtualType

object NRIs : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.BOOLEAN

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node {
        val type = processor.computeString(node.nodes[0], ctx)
        val value = processor.calc(node.nodes[1], ctx)
        return nodeValue(
            node.info,
            if (type.isPrimitive())
                if (value?.isPrimitive == true)
                    type == value.name
                else false
            else if (value?.isPrimitive == true)
                false
            else return NodeIsAs(node.info.withType(IS_), mutableListOf(processor.process(node.nodes[1], ctx, true)!!), value!!, ctx.global.getType(type))
        )
    }
}