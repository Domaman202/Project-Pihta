package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeIncPht
import ru.DmN.pht.utils.computeString
import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processors.INodeProcessor

object NRIncPht : INodeProcessor<NodeIncPht> {
    override fun process(node: NodeIncPht, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val file = processor.computeString(node.nodes[0], ctx)
        return processor.process(Parser(ctx.module.getModuleFile(file), processor.mp).parseNode(node.ctx)!!, ctx, valMode)
    }
}