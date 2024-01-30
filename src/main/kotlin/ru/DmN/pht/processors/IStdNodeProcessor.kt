package ru.DmN.pht.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

interface IStdNodeProcessor<T : Node> : INodeProcessor<T> {
    fun compute(node: T, processor: Processor, ctx: ProcessingContext): Node =
        node

    fun computeInt(node: T, processor: Processor, ctx: ProcessingContext): Int? =
        null

    fun computeString(node: T, processor: Processor, ctx: ProcessingContext): String? =
        null

    fun computeList(node: T, processor: Processor, ctx: ProcessingContext): List<Node>? =
        null

    fun computeType(node: T, processor: Processor, ctx: ProcessingContext): VirtualType? =
        null

    fun computeTypeWithGens(gens: Map<String, VirtualType>, node: T, processor: Processor, ctx: ProcessingContext): VirtualType? =
        null

    fun computeTypes(node: T, processor: Processor, ctx: ProcessingContext): List<VirtualType>? =
        null

    fun computeGenericType(node: T, processor: Processor, ctx: ProcessingContext): String? =
        null
}