package ru.DmN.pht.std.processors

import ru.DmN.pht.std.processor.utils.nodeValueClass
import ru.DmN.pht.std.processor.utils.nodeValueNil
import ru.DmN.pht.std.utils.ofPrimitive
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor

object NRTypeof : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val type = processor.calc(node.nodes.first(), ctx)
        return if (type == null) nodeValueNil(node.token.line) else nodeValueClass(node.token.line, type.ofPrimitive())
    }
}