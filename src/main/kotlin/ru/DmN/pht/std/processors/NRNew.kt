package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeNew
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.line
import ru.DmN.pht.std.utils.processNodes

object NRNew : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType(processor.computeString(node.nodes[0], ctx), processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNew {
        val nodes = processor.processNodes(node, ctx, ValType.VALUE)
        val ctor = NRMCall.findMethod(
            ctx.global.getType(processor.computeString(nodes[0], ctx), processor.tp),
            "<init>",
            nodes.drop(1),
            processor,
            ctx
        )
        return NodeNew(node.token.processed(), NRMCall.processArguments(node.line, processor, ctx, ctor.second, ctor.first), ctor.second)
    }
}