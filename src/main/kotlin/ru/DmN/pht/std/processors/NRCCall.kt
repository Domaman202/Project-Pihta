package ru.DmN.pht.std.processors

import ru.DmN.pht.std.processor.utils.nodeGetOrName
import ru.DmN.pht.std.processor.utils.nodeMCall
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.utils.Platform
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.line

object NRCCall : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.VOID

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        if (ctx.platform == Platform.JAVA) {
            val line = node.line
            NRMCall.process(nodeMCall(line, nodeGetOrName(line, "super"), "<init>", node.nodes), processor, ctx, mode)
        } else node
}