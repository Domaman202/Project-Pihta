package ru.DmN.pht.std.processors

import ru.DmN.pht.std.processor.utils.nodeValueClass
import ru.DmN.pht.std.utils.compute
import ru.DmN.pht.std.utils.computeType
import ru.DmN.pht.std.utils.isConstClass
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.utils.VirtualType

object NRArrayType : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? {
        val type = processor.compute(node.nodes[0], ctx)
        return if (type.isConstClass)
            processor.computeType(type, ctx).arrayType
        else processor.calc(type, ctx)?.arrayType
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE) {
            val type = processor.process(node.nodes[0], ctx, ValType.VALUE)!!
            if (type.isConstClass)
                nodeValueClass(node.info, processor.computeType(type, ctx).arrayType.name)
            else processor.calc(type, ctx)?.let { nodeValueClass(node.info, it.arrayType.name) }
        } else null

    override fun computeString(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): String =
        calc(node, processor, ctx)!!.name

    override fun computeType(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        calc(node, processor, ctx)
}