package ru.DmN.pht.processors

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

interface IInlinableProcessor<T : Node> : INodeProcessor<T> {
    fun isInlinable(node: T, processor: Processor, ctx: ProcessingContext): Boolean

    fun inline(node: T, processor: Processor, ctx: ProcessingContext): Node
}