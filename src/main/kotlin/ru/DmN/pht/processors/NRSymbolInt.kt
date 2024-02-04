package ru.DmN.pht.processors

import ru.DmN.pht.node.nodeValue
import ru.DmN.pht.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.VALUE
import ru.DmN.siberia.utils.VirtualType

object NRSymbolInt : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.INT

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == VALUE)
            nodeValue(node.info, computeString(node, processor, ctx).toInt())
        else null

    override fun computeInt(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): Int =
        computeString(node, processor, ctx).toInt()

    override fun computeString(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): String =
        node.nodes.asSequence().map { processor.computeString(it, ctx) }.reduce { acc, s -> acc + s }
}