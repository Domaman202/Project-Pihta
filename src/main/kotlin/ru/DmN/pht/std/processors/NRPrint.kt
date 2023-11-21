package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.std.processor.utils.nodeMCall
import ru.DmN.pht.std.utils.line

object NRPrint : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList =
        NRMCall.process(nodeMCall(node.line, "ru.DmN.pht.std.utils.StdOut", node.token.text!!, node.nodes), processor, ctx, mode)
}