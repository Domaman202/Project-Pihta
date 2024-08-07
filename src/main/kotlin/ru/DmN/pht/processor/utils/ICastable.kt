package ru.DmN.pht.processor.utils

import ru.DmN.pht.processors.IAdaptableProcessor
import ru.DmN.pht.utils.vtype.VTAuto
import ru.DmN.pht.utils.vtype.VTDynamic
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType

interface ICastable {
    /**
     * -1 -> no castable
     * N -> cast cost (How far is the type from the desired type)
     */
    fun castableTo(to: VirtualType): Int

    companion object {
        fun of(type: VirtualType): ICastable =
            when (type) {
                VTAuto -> CastableAutoImpl
                VTDynamic -> CastableDynamicImpl
                else -> CastableImpl(type)
            }

        fun of(node: Node, processor: Processor, ctx: ProcessingContext): ICastable =
            processor.get(node, ctx).let {
                if (it is IAdaptableProcessor<*>)
                    CastableAdaptImpl(node, it as IAdaptableProcessor<Node>, processor, ctx)
                else of(processor.calc(node, ctx)!!)
            }
    }
}