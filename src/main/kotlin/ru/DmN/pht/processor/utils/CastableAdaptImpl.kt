package ru.DmN.pht.processor.utils

import ru.DmN.pht.processors.IAdaptableProcessor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType

data class CastableAdaptImpl(val node: Node, val np: IAdaptableProcessor<Node>, val processor: Processor, val ctx: ProcessingContext) : ICastable {
    override fun castableTo(type: VirtualType): Int =
        np.adaptableTo(node, type, processor, ctx)
}
