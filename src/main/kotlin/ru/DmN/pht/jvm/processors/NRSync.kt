package ru.DmN.pht.jvm.processors

import ru.DmN.pht.jvm.ast.NodeSync
import ru.DmN.pht.jvm.node.NodeTypes.SYNC_
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRProgn
import ru.DmN.siberia.utils.VirtualType

object NRSync : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRProgn.calc(node, processor, ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeSync =
        NodeSync(node.info.withType(SYNC_), node.nodes.drop(1).toMutableList(), processor.process(node.nodes[0], ctx, ValType.VALUE)!!)
            .apply { processNodesList(this, processor, ctx, mode) }
}