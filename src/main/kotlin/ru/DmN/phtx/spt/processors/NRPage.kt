package ru.DmN.phtx.spt.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.line
import ru.DmN.phtx.spt.processor.ctx.PageContext
import ru.DmN.phtx.spt.processor.utils.window
import ru.DmN.phtx.spt.processor.utils.with

object NRPage : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val name = Variable.tmp(node)
        val context = ctx.with(PageContext(name))
        val line = node.line
        return NRDefault.process(nodeProgn(
            line,
            node.nodes.apply {
                this.add(
                    0,
                    nodeDef(line, name, nodeMCall(line, nodeGetOrName(line, ctx.window.window), "newPage", emptyList()))
                )
            }
        ), processor, context, mode)
    }
}