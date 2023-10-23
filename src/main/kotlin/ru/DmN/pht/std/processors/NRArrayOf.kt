package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.processor.utils.*

object NRArrayOf : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.calc(node.nodes[0], ctx)!!.arrayType

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList? =
        if (mode == ValType.VALUE) {
            val line = node.token.line
            val tmp = Variable.tmp(node)
            NRBody.process(nodeBody(line, ArrayList<Node>().apply {
                this.add(
                    nodeDef(
                        line,
                        tmp,
                        nodeNewArray(line, processor.calc(node.nodes[0], ctx)!!.name, node.nodes.size)
                    )
                )
                this.addAll(node.nodes.mapIndexed { i, it -> nodeASet(line, tmp, i, it) })
                this.add(nodeGetOrNameOf(line, tmp))
            }), processor, ctx, ValType.VALUE)
        } else null
}