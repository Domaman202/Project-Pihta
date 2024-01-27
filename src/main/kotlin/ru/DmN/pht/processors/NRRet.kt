package ru.DmN.pht.std.processors

import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.node.nodeAs
import ru.DmN.pht.std.processor.utils.method
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor

object NRRet : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val info = node.info
        return if (node.nodes.isEmpty())
            NodeNodesList(info.withType(NodeTypes.RET_))
        else NodeNodesList(
            info.withType(NodeTypes.RET_),
            mutableListOf(
                NRAs.process(
                    nodeAs(info, processor.process(node.nodes[0], ctx, ValType.VALUE)!!, ctx.method.rettype.name),
                    processor,
                    ctx,
                    ValType.VALUE
                )!!
            )
        )
    }
}