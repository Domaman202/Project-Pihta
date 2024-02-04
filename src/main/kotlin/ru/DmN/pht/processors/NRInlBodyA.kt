package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeInlBodyA
import ru.DmN.pht.node.NodeTypes.INL_BODY_
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRProgn
import ru.DmN.siberia.utils.VirtualType

object NRInlBodyA : INodeProcessor<NodeInlBodyA> {
    override fun calc(node: NodeInlBodyA, processor: Processor, ctx: ProcessingContext): VirtualType? =
        node.type

    override fun process(node: NodeInlBodyA, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val new = NodeInlBodyA(node.info.withType(INL_BODY_), node.nodes, node.type)
        processNodesList(new, processor, ctx, mode)
        if (new.type == null)
            new.type = NRProgn.calc(new, processor, ctx)
        return new
    }
}