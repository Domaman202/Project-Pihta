package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.ast.NodeMCall.Type.STATIC
import ru.DmN.pht.processor.utils.global
import ru.DmN.pht.utils.node.NodeTypes.MCALL_
import ru.DmN.pht.utils.node.nodeValueClass
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRRange : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("java.util.Iterator", processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNodesList? =
        if (valMode) {
            val info = node.info
            val it = NodeMCall(
                info.withType(MCALL_),
                node.nodes,
                null,
                nodeValueClass(info, "ru.DmN.pht.utils.IteratorUtils"),
                ctx.global.getType(
                    "ru.DmN.pht.utils.IteratorUtils",
                    processor.tp
                ).methods.find { it.name == "range" }!!,
                STATIC
            )
            processNodesList(it, processor, ctx, true)
            it
        } else null
}
