package ru.DmN.pht.processors

import ru.DmN.pht.utils.node.nodeCls
import ru.DmN.pht.utils.node.nodeStatic
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRApp : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node {
        val info = node.info
        return NRClass.process(
            nodeCls(info, "App", "Object", nodeStatic(info, node.nodes)),
            processor,
            ctx,
            valMode
        )
    }
}