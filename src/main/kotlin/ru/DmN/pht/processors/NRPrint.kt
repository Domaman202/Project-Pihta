package ru.DmN.pht.processors

import ru.DmN.pht.processor.utils.processValue
import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.pht.utils.node.nodeMCall
import ru.DmN.pht.utils.node.processed
import ru.DmN.pht.utils.text
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor

object NRPrint : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNodesList =
        when (ctx.platform) {
            JVM -> NRMCall.process(nodeMCall(node.info, "ru.DmN.pht.io.StdOut", node.text, node.nodes), processor, ctx, valMode)
            else -> NodeNodesList(node.info.processed, node.nodes).apply { processValue(this, processor, ctx) }
        }
}