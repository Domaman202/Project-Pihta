package ru.DmN.pht.std.processors

import ru.DmN.pht.std.processor.utils.nodeMCall
import ru.DmN.pht.std.utils.text
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor

object NRPrint : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList =
        NRMCall.process(nodeMCall(node.info, "ru.DmN.pht.std.utils.StdOut", node.text, node.nodes), processor, ctx, mode)
}