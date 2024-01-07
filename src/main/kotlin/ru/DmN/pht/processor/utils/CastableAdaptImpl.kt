package ru.DmN.pht.std.processor.utils

import ru.DmN.pht.processors.IAdaptableProcessor
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.VirtualType

data class CastableAdaptImpl(val node: Node, val np: IAdaptableProcessor<Node>, val processor: Processor, val ctx: ProcessingContext) : ICastable {
    override fun castableTo(to: VirtualType): Int =
        np.adaptableTo(to, node, processor, ctx)
}
