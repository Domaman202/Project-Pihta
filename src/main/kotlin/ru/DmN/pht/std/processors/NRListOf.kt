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
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.nodeArrayOf
import ru.DmN.pht.std.processor.utils.nodeMCall
import ru.DmN.pht.std.utils.line

object NRListOf : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("List", processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE)
            when (ctx.platform) {
                Platform.UNIVERSAL -> node
                Platform.JAVA -> {
                    val line = node.line
                    NRMCall.process(nodeMCall(line, nodeArrayOf(line, node.nodes), "toList", emptyList()), processor, ctx, ValType.VALUE)
                }
                else -> throw UnsupportedOperationException()
            }
        else null
}