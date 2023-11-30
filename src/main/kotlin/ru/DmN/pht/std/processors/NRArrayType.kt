package ru.DmN.pht.std.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.nodeClass
import ru.DmN.pht.std.utils.compute
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.isConstClass

object NRArrayType : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? {
        val type = processor.compute(node.nodes[0], ctx)
        return if (type.isConstClass)
            ctx.global.getType(processor.computeString(type, ctx), processor.tp).arrayType
        else processor.calc(type, ctx)?.arrayType
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE) {
            val type = processor.process(node.nodes[0], ctx, ValType.VALUE)!!
            if (type.isConstClass)
                nodeClass(node.token.line, ctx.global.getType(processor.computeString(type, ctx), processor.tp).arrayType.name)
            else processor.calc(type, ctx)?.let { nodeClass(node.token.line, it.arrayType.name) }
        } else null

    override fun computeString(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): String =
        calc(node, processor, ctx)!!.name
}