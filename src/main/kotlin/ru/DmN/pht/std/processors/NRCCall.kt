package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.Platform
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processor.utils.platform
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.processor.utils.nodeGetOrName
import ru.DmN.pht.std.processor.utils.nodeMCall
import ru.DmN.pht.std.utils.line

object NRCCall : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.VOID

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        if (ctx.platform == Platform.JAVA) {
            val line = node.line
            NRMCall.process(nodeMCall(line, nodeGetOrName(line, "super"), "<init>", node.nodes), processor, ctx, mode)
        } else node
}