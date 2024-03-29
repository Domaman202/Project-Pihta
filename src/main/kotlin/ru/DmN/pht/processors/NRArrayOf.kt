package ru.DmN.pht.processors

import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.utils.node.*
import ru.DmN.pht.utils.vtype.arrayType
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NRArrayOf : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        elementType(node, processor, ctx).arrayType

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        if (valMode) {
            val info = node.info
            val tmp = Variable.tmp(node)
            processor.process(
                nodeBody(info, ArrayList<Node>().apply {
                    this.add(
                        nodeDef(
                            info,
                            tmp,
                            nodeNewArray(info, elementType(node, processor, ctx).name, node.nodes.size)
                        )
                    )
                    this.addAll(node.nodes.mapIndexed { i, it -> nodeASet(info, tmp, i, it) })
                    this.add(nodeGetOrName(info, tmp))
                }),
                ctx,
                true
            )
        } else null

    private fun elementType(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        if (node.nodes.isEmpty())
            ctx.global.getType("Any")
        else processor.calc(node.nodes[0], ctx)!!
}