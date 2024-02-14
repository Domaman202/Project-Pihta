package ru.DmN.pht.jvm.processors

import ru.DmN.pht.jvm.node.NodeTypes.ANN_LIST_
import ru.DmN.pht.processor.utils.processValue
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRList : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.ofKlass("java.lang.Object").arrayType

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList =
        NodeNodesList(node.info.withType(ANN_LIST_), node.nodes).apply { processValue(this, processor, ctx) }
}