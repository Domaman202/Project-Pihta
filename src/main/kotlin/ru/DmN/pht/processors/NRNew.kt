package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeNew
import ru.DmN.pht.node.NodeTypes.NEW_
import ru.DmN.pht.processor.utils.Static
import ru.DmN.pht.processors.NRMCall.findMethod
import ru.DmN.pht.utils.computeType
import ru.DmN.pht.utils.processNodes
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
        val ctor = findMethod(
            type,
            "<init>",
            nodes.drop(1),
            Static.NO_STATIC,
            processor,
            ctx
        )
        val info = node.info
        return NodeNew(
            info.withType(NEW_),
            NRMCall.processArguments(info, processor, ctx, ctor.method, ctor.args, ctor.compression),
            type,
            ctor.method
        )
    }
}