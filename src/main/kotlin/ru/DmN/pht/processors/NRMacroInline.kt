package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeMacroUtil
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.processor.utils.sliceInsert
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRProgn

object NRMacroInline : INodeProcessor<NodeMacroUtil> {
    override fun process(node: NodeMacroUtil, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node {
        val names = processor.computeList(node.nodes[0], ctx).map { processor.computeString(it, ctx) }
        node.nodes.drop(1).forEach { expr ->
            expr as INodesList
            for (i in 0 until expr.nodes.size) {
                val it = expr.nodes[i]
                if (it is NodeMacroUtil && names.any { name -> processor.computeString(it.nodes[0], ctx) == name }) {
                    sliceInsert(expr.nodes, i, (processor.process(it, ctx, valMode)!! as INodesList).nodes)
                }
            }
        }
        return NRProgn.process(
            nodeProgn(node.info, node.nodes.drop(1).toMutableList()),
            processor,
            ctx,
            valMode
        )
    }
}