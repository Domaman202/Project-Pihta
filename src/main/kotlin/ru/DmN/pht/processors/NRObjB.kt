package ru.DmN.pht.processors

import ru.DmN.pht.std.ast.NodeType
import ru.DmN.pht.std.processors.NRClass
import ru.DmN.pht.std.utils.computeType
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRObjB : INodeProcessor<NodeType> {
    override fun calc(node: NodeType, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type
}