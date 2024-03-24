package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeFSet
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.utils.computeString
import ru.DmN.pht.utils.isConstClass
import ru.DmN.pht.utils.node.NodeTypes.FSET_
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

object NRFSetA : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeFSet {
        val instance =
            if (node.nodes[0].isConstClass)
                ctx.global.getType(processor.computeString(node.nodes[0], ctx))
            else processor.calc(node.nodes[0], ctx)!!
        val name = processor.computeString(node.nodes[1], ctx)
        return NodeFSet(
            node.info.withType(FSET_),
            mutableListOf(processor.process(node.nodes[0], ctx, true)!!, processor.process(node.nodes[2], ctx, true)!!),
            instance.fields.find { it.name == name }!!
        )
    }
}