package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeEquals

object NRCompare : INodeProcessor<NodeEquals> {
    override fun calc(node: NodeEquals, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.BOOLEAN

    override fun process(node: NodeEquals, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE)
            NRDefault.processValue(NodeEquals(node.token.processed(), node.nodes), processor, ctx)
        else null
}