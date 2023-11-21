package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processors.NRDefault

/***
 * Simple Annotation
 */
class NRSA(val annotation: (node: Node, processor: Processor, ctx: ProcessingContext) -> Unit) : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList {
        node.nodes.forEach { annotation(it, processor, ctx) }
        val new = NRDefault.process(NodeNodesList(node.token, node.nodes), processor, ctx, mode)
        new.nodes.forEach { annotation(it, processor, ctx) }
        return new
    }
}