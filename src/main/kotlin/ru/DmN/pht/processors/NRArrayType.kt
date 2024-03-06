package ru.DmN.pht.processors

import ru.DmN.pht.utils.compute
import ru.DmN.pht.utils.computeType
import ru.DmN.pht.utils.isConstClass
import ru.DmN.pht.utils.node.nodeValueClass
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType

object NRArrayType : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? {
        val type = processor.compute(node.nodes[0], ctx)
        return if (type.isConstClass)
            processor.computeType(type, ctx).arrayType
        else processor.calc(type, ctx)?.arrayType
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        if (valMode) {
            val type = processor.process(node.nodes[0], ctx, true)!!
            if (type.isConstClass)
                nodeValueClass(node.info, processor.computeType(type, ctx).arrayType.name)
            else processor.calc(type, ctx)?.let { nodeValueClass(node.info, it.arrayType.name) }
        } else null

    override fun computeString(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): String =
        calc(node, processor, ctx)!!.name

    override fun computeType(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        calc(node, processor, ctx)
}