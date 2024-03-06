package ru.DmN.pht.processors

import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.pht.utils.node.nodeMCall
import ru.DmN.pht.utils.node.nodeName
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType
import ru.DmN.siberia.utils.vtype.VirtualType.Companion.VOID

object NRCCall : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VOID

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
        when (ctx.platform) {
            JVM -> {
                val info = node.info
                NRMCall.process(nodeMCall(info, nodeName(info, "super"), "<init>", node.nodes), processor, ctx, valMode)
            }

            else -> node
        }
}