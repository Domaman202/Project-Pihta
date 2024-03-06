package ru.DmN.pht.processors

import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext

interface IInlinableProcessor<T : Node> {
    fun isInlinable(node: T, processor: Processor, ctx: ProcessingContext): Boolean

    fun inline(node: T, processor: Processor, ctx: ProcessingContext): Node
}