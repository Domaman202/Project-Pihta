package ru.DmN.pht.jvm.processors

import ru.DmN.pht.jvm.utils.node.NodeTypes.ANN_LIST_
import ru.DmN.pht.processor.utils.processValue
import ru.DmN.pht.utils.vtype.arrayType
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRList : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.tp.typeOf("Any").arrayType

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNodesList =
        NodeNodesList(node.info.withType(ANN_LIST_), node.nodes).apply { processValue(this, processor, ctx) }
}