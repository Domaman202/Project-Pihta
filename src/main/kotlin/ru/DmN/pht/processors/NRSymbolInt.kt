package ru.DmN.pht.processors

import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.processors.IStdNodeProcessor
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.utils.VirtualType

object NRSymbolInt : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.INT

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE)
            NodeValue.of(node.info, NodeValue.Type.INT, computeString(node, processor, ctx))
        else null

    override fun computeInt(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): Int =
        computeString(node, processor, ctx).toInt()

    override fun computeString(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): String =
        node.nodes.asSequence().map { processor.computeString(it, ctx) }.reduce { acc, s -> acc + s }
}