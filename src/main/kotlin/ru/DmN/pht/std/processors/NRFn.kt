package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeFn
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.utils.computeStringNodes

object NRFn : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("Any", processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val context = ctx.with(BodyContext.of(null))
        val nodes = NRDefault.processValue(node, processor, context).nodes
        val offset = if (nodes[0].isConstClass()) 1 else 0
        val type = if (offset == 1) context.global.getType(nodes[0].getValueAsString(), processor.tp) else null
        return NodeFn(node.token.processed(), nodes.drop(offset + 1).toMutableList(), type, processor.computeStringNodes(nodes[offset], context))
    }
}