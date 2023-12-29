package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeASet
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRASetB : INodeProcessor<NodeASet> {
    override fun calc(node: NodeASet, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.value, ctx)
}