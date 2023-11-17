package ru.DmN.pht.std.processor.utils

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.IAdaptableNode
import ru.DmN.pht.std.utils.VTDynamic

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