package ru.DmN.pht.std.processors

import ru.DmN.pht.std.processor.utils.nodeMCall
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.line

object NRPrint : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList =
        NRMCall.process(nodeMCall(node.line, "ru.DmN.pht.std.utils.StdOut", node.token.text!!, node.nodes), processor, ctx, mode)
}