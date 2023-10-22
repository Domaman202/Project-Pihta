package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.std.ast.IStaticallyNode
import ru.DmN.pht.std.processor.utils.nodeProgn

object NRStatic : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList {
        val new = NRDefault.process(nodeProgn(node.token.line, node.nodes), processor, ctx, mode)
        new.nodes.forEach { if (it is IStaticallyNode) it.static = true }
        return new
    }
}