package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeCompare
import ru.DmN.pht.ast.NodeMath
import ru.DmN.pht.utils.node.NodeTypes.NOT_
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRNot : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        NRCompare.process(
            node,
            { type, nodes ->
                if (type == VirtualType.BOOLEAN)
                    NodeCompare(node.info.withType(NOT_), nodes)
                else NodeMath(node.info.withType(NOT_), nodes, type)
            },
            processor,
            ctx,
            valMode
        )
}