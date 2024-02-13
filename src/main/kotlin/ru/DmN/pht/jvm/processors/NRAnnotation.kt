package ru.DmN.pht.jvm.processors

import ru.DmN.pht.jvm.ast.NodeAnnotation
import ru.DmN.pht.jvm.node.NodeTypes.ANN_ANN_
import ru.DmN.pht.utils.computeType
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRProgn
import ru.DmN.siberia.utils.VirtualType

// TODO: Добавить поддержку аргументов
object NRAnnotation : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRProgn.calc(node, processor, ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        NodeAnnotation(
            node.info.withType(ANN_ANN_),
            if (node.nodes.isEmpty())
                ArrayList()
            else {
                val list =
                    node.nodes
                        .dropLast(1) // todo: drop last for sequence
                        .asSequence()
                        .drop(1)
                        .map { processor.process(it, ctx, ValType.NO_VALUE) }
                        .filterNotNull()
                        .toMutableList()
                processor.process(node.nodes.last(), ctx, ValType.VALUE)?.let { list += it }
                list
            },
            processor.computeType(node.nodes[0], ctx)
        )
}