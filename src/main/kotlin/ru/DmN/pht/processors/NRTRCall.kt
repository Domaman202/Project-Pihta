package ru.DmN.pht.processors

import ru.DmN.pht.processor.utils.method
import ru.DmN.pht.utils.node.NodeTypes.TRCALL_
import ru.DmN.pht.utils.processNodes
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRTRCall : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.method.rettype

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node {
        val nodes = processor.processNodes(node, ctx, valMode)
        return NodeNodesList(node.info.withType(TRCALL_), nodes)
//        return when (ctx.platform) {
//            JVM, UNIVERSAL -> NodeNodesList(node.info.withType(TRCALL_), nodes)
//            else -> NodeMCall(node.info.withType(MCALL_), nodes, null, null, ctx.method, null) // todo:
//        }
    }
}