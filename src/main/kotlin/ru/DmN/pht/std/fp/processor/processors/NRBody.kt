package ru.DmN.pht.std.fp.processor.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.processor.processors.INodeProcessor
import ru.DmN.pht.base.processor.processors.NRDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.processor.utils.bodyOrNull
import ru.DmN.pht.std.base.processor.utils.with
import ru.DmN.pht.std.fp.ast.NodeBody
import ru.DmN.pht.std.fp.processor.ctx.BodyContext

object NRBody : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes.last(), ctx(node as NodeBody, ctx))

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList =
        NodeBody(node.tkOperation, node.copyNodes()).run { NRDefault.process(this, processor, ctx(this, ctx), mode) }

    private fun ctx(node: NodeBody, ctx: ProcessingContext): ProcessingContext =
        ctx.with(node.ctx ?: BodyContext.of(ctx.bodyOrNull).apply { node.ctx = this })
}