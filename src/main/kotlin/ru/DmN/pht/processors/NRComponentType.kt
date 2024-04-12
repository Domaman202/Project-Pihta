package ru.DmN.pht.processors

import ru.DmN.pht.processor.utils.compute
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.utils.isConstClass
import ru.DmN.pht.utils.node.nodeValueClass
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType

object NRComponentType : IStdNodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? {
        val type = processor.compute(node.nodes[0], ctx)
        return (if (type.isConstClass) processor.computeType(type, ctx) else processor.calc(type, ctx))?.componentType
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        if (valMode)
            nodeValueClass(node.info, calc(node, processor, ctx)!!.name)
        else null

    override fun computeString(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): String =
        calc(node, processor, ctx)!!.name

    override fun computeType(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        calc(node, processor, ctx)
}