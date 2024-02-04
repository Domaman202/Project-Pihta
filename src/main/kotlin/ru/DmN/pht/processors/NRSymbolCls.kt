package ru.DmN.pht.processors

import ru.DmN.pht.node.nodeValueClass
import ru.DmN.pht.processor.utils.global
import ru.DmN.pht.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.VALUE
import ru.DmN.siberia.utils.VirtualType

object NRSymbolCls : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("Class", processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == VALUE)
            nodeValueClass(node.info, computeString(node, processor, ctx))
        else null

    override fun computeString(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): String =
        node.nodes.asSequence().map { processor.computeString(it, ctx) }.reduce { acc, s -> acc + s }

    override fun computeType(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType(computeString(node, processor, ctx), processor.tp)
}