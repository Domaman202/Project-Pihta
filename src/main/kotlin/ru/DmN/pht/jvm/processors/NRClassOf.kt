package ru.DmN.pht.jvm.processors

import ru.DmN.pht.ast.NodeClassOf
import ru.DmN.pht.jvm.utils.node.NodeTypes.CLASS_OF_
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRClassOf : INodeProcessor<Node> {
    override fun calc(node: Node, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.tp.typeOf("java.lang.Class")

    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        if (valMode)
            NodeClassOf(node.info.withType(CLASS_OF_), processor.computeType((node as NodeNodesList).nodes[0], ctx))
        else null
}