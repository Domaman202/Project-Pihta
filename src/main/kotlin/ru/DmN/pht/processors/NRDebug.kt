package ru.DmN.pht.processors

import ru.DmN.pht.node.nodePrintln
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRDebug : INodeProcessor<Node> {
    override fun calc(node: Node, processor: Processor, ctx: ProcessingContext): VirtualType? =
        null

    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        NRPrint.process(nodePrintln(node.info, node.info.print("Точка отладки.\nтест!")), processor, ctx, ValType.NO_VALUE)
}