package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.utils.VirtualType

object NRSymbol : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("String", processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE)
            NodeValue.of(node.info, NodeValue.Type.STRING, computeString(node, processor, ctx))
        else null

    override fun computeString(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): String =
        node.nodes.asSequence().map { processor.computeString(it, ctx) }.reduce { acc, s -> acc + s }
}