package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeIsAs
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.utils.node.NodeTypes.AS_
import ru.DmN.pht.utils.vtype.VVTNullable
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.exception.MessageException
import ru.DmN.siberia.utils.vtype.VirtualType
import ru.DmN.siberia.utils.vtype.VirtualType.Companion.VOID

object NRAs : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.computeType(node.nodes[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        if (valMode) {
            val value = processor.process(node.nodes[1], ctx, true)!!
            val np = processor.get(value, ctx)
            val from = np.calc(value, processor, ctx)
            val to = calc(node, processor, ctx)
            //
            if (from == null || from == VOID)
                value
            else if (from.isAssignableFrom(to))
                checkNullable(node, from, to) { value }
            else if (np is IAdaptableProcessor<Node> && np.adaptType.cast)
                np.adaptToType(to, value, processor, ctx)
            else checkNullable(node, from, to) { cast(node, value, from ,to) }
        } else null

    fun cast(to: VirtualType, value: Node, processor: Processor, ctx: ProcessingContext): Node {
        val np = processor.get(value, ctx)
        val from = np.calc(value, processor, ctx)!!
        return if(np is IAdaptableProcessor<Node> && np.adaptType.cast)
            np.adaptToType(to, value, processor, ctx)
        else checkNullable(value, from, to) { cast(value, value, from, to) }
    }


    fun castFrom(from: VirtualType, to: VirtualType, value: Node, processor: Processor, ctx: ProcessingContext): Node =
        processor.get(value, ctx).let {
            if (it is IAdaptableProcessor<Node> && it.adaptType.cast)
                it.adaptToType(to, value, processor, ctx)
            else checkNullable(value, from, to) { cast(value, value, from, to) }
        }

    private inline fun checkNullable(node: Node, from: VirtualType, to: VirtualType, block: () -> Node) =
        if (from is VVTNullable)
            if (to is VVTNullable)
                if (from.nullable == to.nullable || to.nullable)
                    block()
                else throw MessageException(null, "Приведение типа 'nullable' к типу 'not-null'!")
            else cast(node, block(), from, to)
        else block()

    @Suppress("NOTHING_TO_INLINE")
    private inline fun cast(node: Node, value: Node, from: VirtualType, to: VirtualType) =
        NodeIsAs(node.info.withType(AS_), mutableListOf(value), from, to)
}