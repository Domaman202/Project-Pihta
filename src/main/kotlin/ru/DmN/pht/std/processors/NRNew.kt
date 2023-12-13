package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeGensNodesList
import ru.DmN.pht.std.ast.NodeNew
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.processNodes
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.line

object NRNew : INodeProcessor<NodeGensNodesList> {
    override fun calc(node: NodeGensNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType(processor.computeString(node.nodes[0], ctx), processor.tp)

    override fun process(node: NodeGensNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNew {
        val nodes = processor.processNodes(node, ctx, ValType.VALUE)
        val ctor = NRMCall.findMethod(
            ctx.global.getType(processor.computeString(nodes[0], ctx), processor.tp),
            "<init>",
            nodes.drop(1),
            processor,
            ctx
        )
        return NodeNew(node.token.processed(), NRMCall.processArguments(node.line, processor, ctx, ctor.second, ctor.first), node.generics, ctor.second)
    }
}