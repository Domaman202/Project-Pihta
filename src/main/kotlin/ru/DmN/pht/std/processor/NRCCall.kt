package ru.DmN.pht.std.processor

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.Platform
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.platform
import ru.DmN.pht.std.processor.utils.nodeGetOrNameOf
import ru.DmN.pht.std.processor.utils.nodeMCall
import ru.DmN.pht.std.ups.NUPMCallA
import ru.DmN.pht.std.utils.line

object NRCCall : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.VOID

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        if (ctx.platform == Platform.JAVA) {
            val line = node.line
            NUPMCallA.process(nodeMCall(line, nodeGetOrNameOf(line, "super"), "<init>", node.nodes), processor, ctx, mode)
        } else node
}