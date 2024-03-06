package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeNamedList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRBreakContinue : INodeProcessor<Node> {
    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNamedList =
        NRNamedList.process(node as NodeNodesList, processor, ctx, valMode)
}