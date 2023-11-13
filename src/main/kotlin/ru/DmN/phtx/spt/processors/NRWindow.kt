package ru.DmN.phtx.spt.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.Platform
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.platform
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.line
import ru.DmN.phtx.spt.processor.ctx.WindowContext
import ru.DmN.phtx.spt.processor.utils.with

object NRWindow : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("ru.DmN.phtx.spt.Window", processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        when (ctx.platform) {
            Platform.UNIVERSAL -> node
            Platform.JAVA -> {
                val name = Variable.tmp(node)
                val context = ctx.with(WindowContext(name))
                val line = node.line
                NRDefault.process(nodeProgn(
                    line,
                    node.nodes.asSequence().drop(3).toMutableList().apply {
                        this.add(
                            0,
                            nodeDef(line, name, nodeNew(line, "ru.DmN.phtx.spt.Window", node.nodes.dropLast(node.nodes.size - 3)))
                        )
                        this += nodeGetOrName(line, name)
                    }
                ), processor, context, mode)
            }
            else -> throw UnsupportedOperationException("Unsupported platform ${ctx.platform}")
        }
}