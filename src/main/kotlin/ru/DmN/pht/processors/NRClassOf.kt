package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeClassOf
import ru.DmN.pht.node.NodeTypes
import ru.DmN.pht.utils.computeType
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRClassOf : INodeProcessor<Node> {
    override fun calc(node: Node, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.tp.typeOf("java.lang.Class")

    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE)
            NodeClassOf(node.info.withType(NodeTypes.CLASS_OF_), processor.computeType((node as NodeNodesList).nodes[0], ctx).name)
        else null
}