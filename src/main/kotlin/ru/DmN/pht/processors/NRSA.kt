package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeModifierNodesList
import ru.DmN.pht.std.node.processed
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor

/***
 * Simple Annotation
 */
class NRSA(val annotation: (node: Node, processor: Processor, ctx: ProcessingContext) -> Unit) : INodeProcessor<NodeModifierNodesList> {
    override fun process(node: NodeModifierNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeModifierNodesList {
        node.nodes.forEach { annotation(it, processor, ctx) }
        val new = NodeModifierNodesList(node.info.processed, node.copyNodes())
        processNodesList(new,  processor, ctx, mode)
        new.nodes.forEach { annotation(it, processor, ctx) }
        return new
    }
}