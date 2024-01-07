package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeFSet
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.isConstClass
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor

object NRFSetA : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeFSet {
        val instance =
            if (node.nodes[0].isConstClass)
                ctx.global.getType(processor.computeString(node.nodes[0], ctx), processor.tp)
            else processor.calc(node.nodes[0], ctx)!!
        val name = processor.computeString(node.nodes[1], ctx)
        return NodeFSet(
            node.info.withType(NodeTypes.FSET_),
            mutableListOf(processor.process(node.nodes[0], ctx, ValType.VALUE)!!, processor.process(node.nodes[2], ctx, ValType.NO_VALUE)!!),
            instance.fields.find { it.name == name }!!
        )
    }
}