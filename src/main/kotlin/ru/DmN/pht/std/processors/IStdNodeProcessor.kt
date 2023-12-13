package ru.DmN.pht.std.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

interface IStdNodeProcessor<T : Node> : INodeProcessor<T> {
    fun compute(node: T, processor: Processor, ctx: ProcessingContext): Node =
        node

    fun computeString(node: T, processor: Processor, ctx: ProcessingContext): String? =
        null

    fun computeInt(node: T, processor: Processor, ctx: ProcessingContext): Int? =
        null

    fun computeList(node: T, processor: Processor, ctx: ProcessingContext): List<Node>? =
        null
}