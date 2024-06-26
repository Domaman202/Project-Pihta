package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeCatch
import ru.DmN.pht.processor.ctx.BodyContext
import ru.DmN.pht.processor.ctx.body
import ru.DmN.pht.processor.ctx.with
import ru.DmN.pht.processor.utils.compute
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.utils.dropMutable
import ru.DmN.pht.utils.isConstClass
import ru.DmN.pht.utils.node.NodeTypes.CATCH_
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRCatch : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        if (processor.compute(node.nodes[0], ctx).isConstClass)
            processor.computeType(node.nodes[0], ctx)
        else null

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeCatch {
        val bctx = ctx.body
        val info = node.info
        val offset =
            if (processor.compute(node.nodes[0], ctx).isConstClass)
                1
            else 0
        return NodeCatch(
            info.withType(CATCH_),
            node.nodes.dropMutable(offset + 1),
            if (valMode && offset == 1) processor.computeType(node.nodes[0], ctx) else null,
            processor.computeList(node.nodes[offset], ctx).map {
                val catcher = processor.computeList(it, ctx)
                val variable = processor.computeString(catcher[0], ctx)
                val type = processor.computeType(catcher[1], ctx)
                Triple(
                    variable,
                    type,
                    processor.process(
                        nodeProgn(info, catcher.dropMutable(2)),
                        ctx.with(BodyContext.of(bctx).apply {
                            if (variable != "_") {
                                addVariable(variable, type)
                            }
                        }),
                        valMode
                    )
                )
            }
        ).apply { processNodesList(this, processor, ctx, valMode) }
    }
}