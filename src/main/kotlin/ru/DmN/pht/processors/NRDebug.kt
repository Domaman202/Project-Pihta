package ru.DmN.pht.processors

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.exception.MessageException

object NRDebug : INodeProcessor<Node> {
    private var i = 0

    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
        if (i < 1) {
            i++
            node
        } else throw MessageException(null, "Debug ${if (valMode) "(val)" else "(no val())"}")
}