package ru.DmN.pht.processors

import ru.DmN.pht.utils.node.nodePrintln
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.console.BuildCommands.provider
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRDebug : INodeProcessor<Node> {
    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        processor.process(nodePrintln(node.info, node.info.print(::provider)), ctx, false)
}