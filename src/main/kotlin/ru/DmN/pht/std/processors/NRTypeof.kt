package ru.DmN.pht.std.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.utils.ofPrimitive

object NRTypeof : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val type = processor.calc(node.nodes.first(), ctx)
        return if (type == null)
            NodeValue.of(node.token.line, NodeValue.Type.NIL, "null")
        else NodeValue.of(node.token.line, NodeValue.Type.CLASS, type.ofPrimitive())
    }
}