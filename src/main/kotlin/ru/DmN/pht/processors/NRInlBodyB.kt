package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeInlBodyA
import ru.DmN.pht.node.NodeTypes
import ru.DmN.pht.utils.computeType
import ru.DmN.pht.utils.processNodes
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRInlBodyB : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.computeType(node.nodes[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeInlBodyA {
        val nodes = processor.processNodes(node, ctx, mode)
        return NodeInlBodyA(node.info.withType(NodeTypes.INL_BODY_), nodes.drop(1).toMutableList(), processor.computeType(nodes[0], ctx))
    }
}