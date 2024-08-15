package ru.DmN.pht.processors

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

interface IAdaptableProcessor<T : Node> : INodeProcessor<T> {
    val adaptType: Type
        get() = Type.ANY

    fun adaptableTo(node: T, type: VirtualType, processor: Processor, ctx: ProcessingContext): Int
    fun adaptToType(node: T, type: VirtualType, processor: Processor, ctx: ProcessingContext): Node

    enum class Type(val cast: Boolean, val argument: Boolean) {
        CAST(true, false),
        ARGUMENT(false, true),
        ANY(true, true)
    }
}