package ru.DmN.pht.processors

import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.utils.InlineVariable
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor

object NRInlDef : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val bctx = ctx.body
        processor.computeList(node.nodes[0], ctx).map { processor.computeList(it, ctx) }.forEach {
            process(processor.computeString(it[0], ctx), it[1], bctx)
        }
        return null
    }

    fun process(name: String, value: Node, bctx: BodyContext) {
        bctx.variables += InlineVariable(name, bctx.nvi.getAndIncrement(), value)
    }
}