package ru.DmN.pht.processors

import ru.DmN.pht.utils.node.nodeMCall
import ru.DmN.pht.utils.text
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRPrint : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNodesList =
        NRMCall.process(nodeMCall(node.info, "ru.DmN.pht.io.StdOut", node.text, node.nodes), processor, ctx, valMode)
}