package ru.DmN.pht.jvm.processors

import ru.DmN.pht.processor.ctx.getType
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.processors.IStdNodeProcessor
import ru.DmN.pht.utils.node.nodeValueClass
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType

object NRSymbolCls : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("Class")

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        if (valMode)
            nodeValueClass(node.info, computeType(node, processor, ctx).cname)
        else null

    override fun computeString(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): String =
        node.nodes.asSequence().map { processor.computeString(it, ctx) }.reduce { acc, s -> acc + s }

    override fun computeType(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.getType(computeString(node, processor, ctx), processor, ctx)
}