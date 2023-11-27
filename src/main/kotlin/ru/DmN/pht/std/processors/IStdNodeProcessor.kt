package ru.DmN.pht.std.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

interface IStdNodeProcessor<T : Node> : INodeProcessor<T> {
    fun compute(node: T, processor: Processor, ctx: ProcessingContext): Node =
        node

    val isComputeString: Boolean
        get() = false

    fun computeString(node: T, processor: Processor, ctx: ProcessingContext): String =
        throw UnsupportedOperationException()

    val isComputeInt: Boolean
        get() = false

    fun computeInt(node: T, processor: Processor, ctx: ProcessingContext): Int =
        throw UnsupportedOperationException()

    val isComputeList: Boolean
        get() = false

    fun computeList(node: T, processor: Processor, ctx: ProcessingContext): List<Node> =
        throw UnsupportedOperationException()
}