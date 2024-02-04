package ru.DmN.pht.processors

import ru.DmN.pht.node.NodeTypes.RET_
import ru.DmN.pht.node.nodeAs
import ru.DmN.pht.processor.utils.method
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.VALUE
import ru.DmN.siberia.processors.INodeProcessor

object NRRet : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val info = node.info
        return if (node.nodes.isEmpty())
            NodeNodesList(info.withType(RET_))
        else NodeNodesList(
            info.withType(RET_),
            mutableListOf(
                NRAs.process(
                    nodeAs(info, processor.process(node.nodes[0], ctx, VALUE)!!, ctx.method.rettype.name),
                    processor,
                    ctx,
                    VALUE
                )!!
            )
        )
    }
}