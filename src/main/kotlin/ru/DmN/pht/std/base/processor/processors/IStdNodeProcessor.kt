package ru.DmN.pht.std.base.processor.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.processors.INodeProcessor

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