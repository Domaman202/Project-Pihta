package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processors.INodeProcessor

interface IStdNodeProcessor<T : Node> : INodeProcessor<T> {
    fun compute(node: T, processor: Processor, ctx: ProcessingContext): Node =
        node
    fun computeString(node: T, processor: Processor, ctx: ProcessingContext): String =
        throw UnsupportedOperationException()

    fun computeInt(node: T, processor: Processor, ctx: ProcessingContext): Int =
        throw UnsupportedOperationException()

    fun computeList(node: T, processor: Processor, ctx: ProcessingContext): List<Node> =
        throw UnsupportedOperationException()
}