package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeInner
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.computeType
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor

object NRInner : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeInner {
        val pair = processor.computeList(node.nodes[0], ctx)
        return NodeInner(node.info.withType(NodeTypes.INNER_), processor.computeString(pair[0], ctx), processor.computeType(pair[1], ctx).name)
    }
}