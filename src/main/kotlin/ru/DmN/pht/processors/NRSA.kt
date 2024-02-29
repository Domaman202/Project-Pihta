package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeMetaNodesList
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
class NRSA(val annotation: (node: Node, processor: Processor, ctx: ProcessingContext) -> Unit) : INodeProcessor<NodeMetaNodesList> {
    override fun process(node: NodeMetaNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeMetaNodesList {
        node.nodes.forEach { annotation(it, processor, ctx) }
        val new = NodeMetaNodesList(node.info.processed, node.nodes.mapMutable { it.copy() })
        processNodesList(new,  processor, ctx, valMode)
        new.nodes.forEach { annotation(it, processor, ctx) }
        return new
    }
}