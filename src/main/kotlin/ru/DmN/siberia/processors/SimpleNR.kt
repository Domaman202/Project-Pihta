package ru.DmN.siberia.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.utils.VirtualType

abstract class SimpleNR<T : NodeNodesList> : INodeProcessor<T> {
    override fun calc(node: T, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes.last(), ctx)

    override fun process(node: T, processor: Processor, ctx: ProcessingContext, mode: ValType): T {
        var i = 0
        while (i < node.nodes.size) {
            val it = processor.process(node.nodes[i], ctx, if (i + 1 < node.nodes.size) ValType.NO_VALUE else mode)
            if (it == null) {
                node.nodes.removeAt(i)
                i--
            } else node.nodes[i] = it
            i++
        }
        return node
    }

    fun processValue(node: T, processor: Processor, ctx: ProcessingContext): T {
        for (i in 0 until node.nodes.size)
            node.nodes[i] = processor.process(node.nodes[i], ctx, ValType.VALUE)!!
        return node
    }
}