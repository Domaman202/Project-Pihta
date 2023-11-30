package ru.DmN.pht.std.processor.utils

import ru.DmN.pht.std.ast.IAdaptableNode
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.VTDynamic
import ru.DmN.siberia.utils.VirtualType

interface ICastable {
    /**
     * -1 -> no castable
     * N -> cast cost (How far is the type from the desired type)
     */
    fun castableTo(to: VirtualType): Int

    companion object {
        fun of(type: VirtualType): ICastable =
            if (type == VTDynamic)
                CastableDynamicImpl()
            else CastableImpl(type)

        fun of(node: IAdaptableNode): ICastable =
            CastableAdaptImpl(node)

        fun of(node: Node, processor: Processor, ctx: ProcessingContext): ICastable =
            if (node is IAdaptableNode)
                of(node)
            else of(processor.tp.typeOf(processor.calc(node, ctx)!!.name))
    }
}