package ru.DmN.phtx.ppl.processors

import ru.DmN.pht.node.nodeGetVariable
import ru.DmN.pht.node.nodeMCall
import ru.DmN.pht.node.nodeNew
import ru.DmN.pht.processors.NRMCall
import ru.DmN.pht.processors.NRNew
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.NO_VALUE
import ru.DmN.siberia.processor.utils.ValType.VALUE
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

class NRSimplePage(val type: String) : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.tp.typeOf(type)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val info = node.info
        val new = nodeNew(info, type, node.nodes)
        return if (mode == VALUE)
            new
        else NRMCall.process(
            nodeMCall(
                info,
                nodeGetVariable(info, "phtx\$ppl\$presentation"),
                "plusAssign",
                listOf(new)
            ),
            processor,
            ctx,
            NO_VALUE
        )
    }
}