package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeNew
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.utils.computeType
import ru.DmN.pht.std.utils.processNodes
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRNew : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.computeType(node.nodes[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNew {
        val nodes = processor.processNodes(node, ctx, ValType.VALUE)
        val type = calc(node, processor, ctx)
        val ctor = NRMCall.findMethod(
            type,
            "<init>",
            nodes.drop(1),
            processor,
            ctx
        )
        val info = node.info
        return NodeNew(
            info.withType(NodeTypes.NEW_),
            NRMCall.processArguments(info, processor, ctx, ctor.second, ctor.first),
            type,
            ctor.second
        )
    }
}