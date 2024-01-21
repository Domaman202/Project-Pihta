package ru.DmN.phtx.ppl.processors

import ru.DmN.pht.std.node.nodeGetVariable
import ru.DmN.pht.std.node.nodeMCall
import ru.DmN.pht.std.node.nodeNew
import ru.DmN.pht.std.processors.NRMCall
import ru.DmN.pht.std.processors.NRNew
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.NO_VALUE
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

class NRSimpleElement(val type: String) : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.tp.typeOf(type)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val info = node.info
        return NRMCall.process(
            nodeMCall(
                info,
                nodeGetVariable(info, "phtx\$ppl\$page"),
                "plusAssign",
                listOf(nodeNew(info, type, node.nodes))
            ),
            processor,
            ctx,
            NO_VALUE
        )
    }
}