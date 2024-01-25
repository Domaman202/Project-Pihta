package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeInlBodyA
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.utils.processNodes
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRProgn
import ru.DmN.siberia.utils.VirtualType

object NRInlBodyA : INodeProcessor<NodeInlBodyA> {
    override fun calc(node: NodeInlBodyA, processor: Processor, ctx: ProcessingContext): VirtualType? =
        node.type

    override fun process(node: NodeInlBodyA, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val new = NodeInlBodyA(node.info.withType(NodeTypes.INL_BODY_), processor.processNodes(node, ctx, mode), node.type)
        if (new.type == null)
            new.type = NRProgn.calc(new, processor, ctx)
        return new
    }
}