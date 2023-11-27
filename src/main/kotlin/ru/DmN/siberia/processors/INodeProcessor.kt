package ru.DmN.siberia.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.utils.VirtualType

interface INodeProcessor<T : Node> {
    fun calc(node: T, processor: Processor, ctx: ProcessingContext): VirtualType? =
        null
    fun process(node: T, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        node
}