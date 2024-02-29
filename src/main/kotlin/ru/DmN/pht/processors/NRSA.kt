package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeModifierNodesList
import ru.DmN.pht.utils.mapMutable
import ru.DmN.pht.utils.node.processed
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor

/***
 * Simple Annotation
 */
class NRSA(val annotation: (node: Node, processor: Processor, ctx: ProcessingContext) -> Unit) : INodeProcessor<NodeModifierNodesList> {
    override fun process(node: NodeModifierNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeModifierNodesList {
        node.nodes.forEach { annotation(it, processor, ctx) }
        val new = NodeModifierNodesList(node.info.processed, node.nodes.mapMutable { it.copy() })
        processNodesList(new,  processor, ctx, valMode)
        new.nodes.forEach { annotation(it, processor, ctx) }
        return new
    }
}