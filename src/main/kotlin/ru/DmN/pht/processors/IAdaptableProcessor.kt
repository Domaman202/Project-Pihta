package ru.DmN.pht.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType

interface IAdaptableProcessor<T : Node> {
    fun adaptableTo(type: VirtualType, node: T, processor: Processor, ctx: ProcessingContext): Int
    fun adaptToType(type: VirtualType, node: T, processor: Processor, ctx: ProcessingContext): Node
}