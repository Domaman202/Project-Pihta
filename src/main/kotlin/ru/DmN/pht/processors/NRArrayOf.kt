package ru.DmN.pht.std.processors

import ru.DmN.pht.std.node.*
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType

object NRArrayOf : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        elementType(node, processor, ctx).arrayType

    private fun elementType(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        if (node.nodes.isEmpty())
            ctx.global.getType("Any", processor.tp)
        else processor.calc(node.nodes[0], ctx)!!

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList? =
        if (mode == ValType.VALUE) {
            val info = node.info
            val tmp = Variable.tmp(node)
            NRBody.process(nodeBody(info, ArrayList<Node>().apply {
                this.add(nodeDef(info, tmp, nodeNewArray(info, elementType(node, processor, ctx).name, node.nodes.size)))
                this.addAll(node.nodes.mapIndexed { i, it -> nodeASet(info, tmp, i, it) })
                this.add(nodeGetOrName(info, tmp))
            }), processor, ctx, ValType.VALUE)
        } else null
}