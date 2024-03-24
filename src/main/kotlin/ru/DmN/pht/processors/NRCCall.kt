package ru.DmN.pht.processors

import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.utils.processValue
import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.pht.utils.node.NodeTypes.CCALL_
import ru.DmN.pht.utils.node.nodeMCall
import ru.DmN.pht.utils.node.nodeName
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRCCall : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("void")

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        when (ctx.platform) {
            JVM -> {
                val info = node.info
                processor.process(nodeMCall(info, nodeName(info, "super"), "<init>", node.nodes), ctx, valMode)
            }

            else -> NodeNodesList(node.info.withType(CCALL_), node.nodes).apply { processValue(this, processor, ctx) }
        }
}