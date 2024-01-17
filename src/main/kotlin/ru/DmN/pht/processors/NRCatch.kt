package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeCatch
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.utils.*
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRProgn
import ru.DmN.siberia.utils.VirtualType
import kotlin.streams.toList

object NRCatch : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        if (processor.compute(node.nodes[0], ctx).isConstClass)
            processor.computeType(node.nodes[0], ctx)
        else null

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeCatch {
        val bctx = ctx.body
        val info = node.info
        val offset =
            if (processor.compute(node.nodes[0], ctx).isConstClass)
                1
            else 0
        return NodeCatch(
            info.withType(NodeTypes.CATCH_),
            node.nodes.stream().skip(offset + 1L).toList() as MutableList<Node>,
            if (offset == 1) processor.computeType(node.nodes[0], ctx) else null,
            processor.computeList(node.nodes[offset], ctx).map {
                val catcher = processor.computeList(it, ctx)
                val variable = processor.computeString(catcher[0], ctx)
                val type = processor.computeType(catcher[1], ctx)
                Triple(
                    variable,
                    type,
                    processor.process(
                        nodeProgn(info, catcher.drop(2).toMutableList()),
                        ctx.with(BodyContext.of(bctx).apply {
                            if (variable != "_") {
                                addVariable(variable, type)
                            }
                        }),
                        mode
                    )
                )
            }
        ).apply { processNodesList(this, processor, ctx, mode) }
    }
}