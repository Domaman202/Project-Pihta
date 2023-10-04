package ru.DmN.pht.std.fp.processor.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.processors.INodeProcessor
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.std.fp.ast.NodeValue
import ru.DmN.pht.std.base.utils.ofPrimitive

object NRTypeof : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val type = processor.calc(node.nodes.first(), ctx)
        return if (type == null)
            NodeValue.of(node.tkOperation.line, NodeValue.Type.NIL, "null")
        else NodeValue.of(node.tkOperation.line, NodeValue.Type.CLASS, type.ofPrimitive())
    }
}