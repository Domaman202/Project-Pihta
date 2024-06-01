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
                if (from is VVTNullable && to is VVTNullable)
                    if (from.nullable == to.nullable || to.nullable)
                        value
                    else throw MessageException(null, "Приведение типа 'nullable' к типу 'not-null'!")
                else value
            else if (np is IAdaptableProcessor<Node> && np.adaptType.cast)
                np.adaptToType(to, value, processor, ctx)
            else NodeIsAs(node.info.withType(AS_), mutableListOf(value), from, to)
        } else null
}