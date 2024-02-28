package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeInlBodyA
import ru.DmN.pht.utils.computeType
import ru.DmN.pht.utils.node.NodeTypes.INL_BODY_
import ru.DmN.pht.utils.processNodes
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRInlBodyB : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.computeType(node.nodes[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeInlBodyA {
        val nodes = processor.processNodes(node, ctx, valMode)
        return NodeInlBodyA(node.info.withType(INL_BODY_), nodes.drop(1).toMutableList(), processor.computeType(nodes[0], ctx))
    }
}