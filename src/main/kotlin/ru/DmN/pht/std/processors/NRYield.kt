package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.utils.VirtualType

object NRYield : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        processor.process(node.nodes[0], ctx, mode)
}