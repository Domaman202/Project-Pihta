package ru.DmN.pht.processors

import ru.DmN.pht.processor.ctx.method
import ru.DmN.pht.processor.ctx.retValWrap
import ru.DmN.pht.utils.node.NodeTypes.RET_
import ru.DmN.pht.utils.node.nodeAs
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.processors.INodeProcessor

object NRRet : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node {
        val info = node.info
        return if (node.nodes.isEmpty()) {
            ctx.retValWrap?.let {
                nodeProgn(
                    info,
                    mutableListOf<Node>(NodeNodesList(info.withType(RET_))).apply {
                        it.invoke(null, processor, ctx)?.let(this::add)
                    }
                )
            } ?: NodeNodesList(info.withType(RET_))
        } else {
            var value: Node = nodeAs(info, node.nodes[0], ctx.method.rettype.name)
            ctx.retValWrap?.let { it -> it.invoke(value, processor, ctx)?.let { value = it } }
            NodeNodesList(info.withType(RET_), mutableListOf(processor.process(value, ctx, true)!!))
        }
    }
}