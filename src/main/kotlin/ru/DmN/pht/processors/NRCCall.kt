package ru.DmN.pht.std.processors

import ru.DmN.pht.std.node.nodeGetOrName
import ru.DmN.pht.std.node.nodeMCall
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.Platforms
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRCCall : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.VOID

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        when (ctx.platform) {
            Platforms.JAVA -> {
                val info = node.info
                NRMCall.process(nodeMCall(info, nodeGetOrName(info, "super"), "<init>", node.nodes), processor, ctx, mode)
            }

            else -> node
        }
}