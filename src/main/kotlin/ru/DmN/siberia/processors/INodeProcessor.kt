package ru.DmN.siberia.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.utils.VirtualType

interface INodeProcessor<T : Node> {
    fun calc(node: T, processor: Processor, ctx: ProcessingContext): VirtualType? =
        null
    fun process(node: T, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        node
}