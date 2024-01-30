package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeAGet
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRAGetB : INodeProcessor<NodeAGet> {
    override fun calc(node: NodeAGet, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.arr, ctx)!!.componentType
}