package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processor.ValType
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