package ru.DmN.pht.processors

import ru.DmN.pht.utils.node.nodePrintln
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRDebug : INodeProcessor<Node> {
    override fun calc(node: Node, processor: Processor, ctx: ProcessingContext): VirtualType? =
        null

    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
        NRPrint.process(nodePrintln(node.info, node.info.print("Точка отладки.\nтест!")), processor, ctx, false)
}