package ru.DmN.pht.std.collections.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.processor.processors.INodeProcessor
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.processor.utils.*
import ru.DmN.pht.std.fp.processor.processors.NRBody

object NRArrayOf : INodeProcessor<NodeNodesList> { // todo: нахождение среднего типа
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList? =
        if (mode == ValType.VALUE) {
            val line = node.tkOperation.line
            NRBody.process(nodeBodyOf(line, ArrayList<Node>().apply {
                this.add(
                    nodeDefOf(
                        line,
                        "pht\$tmp",
                        nodeNewArray(line, calc(node, processor, ctx)!!.name, node.nodes.size)
                    )
                )
                this.addAll(node.nodes.mapIndexed { i, it -> nodeASet(line, "pht\$tmp", i, it) })
                this.add(nodeGetOrNameOf(line, "pht\$tmp"))
            }), processor, ctx, ValType.VALUE)
        } else null
}