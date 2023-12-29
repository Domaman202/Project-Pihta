package ru.DmN.pht.std.processors

import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType

object NRArrayOfType : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[0], ctx)!!.arrayType

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE) {
            val info = node.info
            val tmp = Variable.tmp(node)
            NRBody.process(nodeBody(info, ArrayList<Node>().apply {
                this.add(
                    nodeDef(
                        info,
                        tmp,
                        nodeNewArray(info, processor.computeString(node.nodes[0], ctx), node.nodes.size - 1)
                    )
                )
                this.addAll(node.nodes.asSequence().drop(1).mapIndexed { i, it -> nodeASet(info, tmp, i, it) })
                this.add(nodeGetOrName(info, tmp))
            }), processor, ctx, ValType.VALUE)
        } else null
}