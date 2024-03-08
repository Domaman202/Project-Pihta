package ru.DmN.pht.processors

import ru.DmN.pht.processor.ctx.method
import ru.DmN.pht.utils.node.NodeTypes.RET_
import ru.DmN.pht.utils.node.nodeAs
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRRet : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node {
        val info = node.info
        return if (node.nodes.isEmpty())
            NodeNodesList(info.withType(RET_))
        else NodeNodesList(
            info.withType(RET_),
            mutableListOf(
                NRAs.process(
                    nodeAs(info, processor.process(node.nodes[0], ctx, true)!!, ctx.method.rettype.name),
                    processor,
                    ctx,
                    true
                )!!
            )
        )
    }
}