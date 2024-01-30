package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeDefAnnotation
import ru.DmN.pht.node.NodeTypes
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.Platforms.JVM
import ru.DmN.siberia.processor.utils.Platforms.UNIVERSAL
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor

object NRDefAnn : INodeProcessor<NodeDefAnnotation> {
    override fun process(node: NodeDefAnnotation, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        return when (ctx.platform) {
            JVM -> NodeDefAnnotation(node.info.withType(NodeTypes.DEF_ANN_), node.name, node.data)
            UNIVERSAL -> node
            else -> null
        }
    }
}