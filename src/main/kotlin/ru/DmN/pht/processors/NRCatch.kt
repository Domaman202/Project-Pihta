package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeCatch
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.computeType
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
        NRProgn.calc(node, processor, ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeCatch {
        val bctx = ctx.body
        val info = node.info
        return NodeCatch(
            info.withType(NodeTypes.CATCH_),
            node.nodes.stream().skip(1).toList() as MutableList<Node>,
            processor.computeList(node.nodes[0], ctx).map {
                val catcher = processor.computeList(it, ctx)
                val variable = processor.computeString(catcher[0], ctx)
                val type = processor.computeType(catcher[1], ctx)
                Triple(
                    variable,
                    type,
                    processor.process(nodeProgn(info, catcher.drop(2).toMutableList()), ctx.with(BodyContext.of(bctx).apply { addVariable(variable, type) }), mode)
                )
            }
        ).apply { processNodesList(this, processor, ctx, mode) }
    }
}