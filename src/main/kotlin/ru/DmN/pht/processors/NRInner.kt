package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeInner
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.utils.node.NodeTypes.INNER_
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRInner : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeInner {
        val pair = processor.computeList(node.nodes[0], ctx)
        return NodeInner(node.info.withType(INNER_), processor.computeString(pair[0], ctx), processor.computeType(pair[1], ctx).name)
    }
}