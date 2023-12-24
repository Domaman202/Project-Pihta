package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.nodeValueClass
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRRange : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("java.util.Iterator", processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList? =
        if (mode == ValType.VALUE) {
            val info = node.info
            val it = NodeMCall(
                info.withType(NodeTypes.MCALL_),
                node.nodes,
                null,
                nodeValueClass(info, "ru.DmN.pht.std.utils.IteratorUtils"),
                ctx.global.getType(
                    "ru.DmN.pht.std.utils.IteratorUtils",
                    processor.tp
                ).methods.find { it.name == "range" }!!,
                NodeMCall.Type.STATIC
            )
            processNodesList(it, processor, ctx, mode)
            it
        } else null
}
