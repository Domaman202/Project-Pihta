package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.utils.VirtualField
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeRFn
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.utils.LazyProcessValueList
import ru.DmN.pht.std.utils.computeStringNodes
import kotlin.math.absoluteValue

object NRRFn : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("Any", processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeRFn {
        val gctx = ctx.global
        val bctx = ctx.body
        val fakeType = VirtualType(gctx.name("PhtLambda${node.hashCode().absoluteValue}"))
        val context = ctx.with(BodyContext.of(null)).with(fakeType)
        val nodes = LazyProcessValueList(node, processor, context)
        val offset = if (nodes[0].isConstClass()) 1 else 0
        val type = if (offset == 1) gctx.getType(nodes[0].getValueAsString(), processor.tp) else null
        val refs = processor.computeStringNodes(nodes[offset], context)
            .map { bctx[it]!!.apply { fakeType.fields += VirtualField(fakeType, name, type(), static = false, enum = false) } }
        val args = processor.computeStringNodes(nodes[offset + 1], context)
        val body = nodes.dropAndProcess(offset + 2).toMutableList()
        return NodeRFn(node.token.processed(), body, type, args, fakeType.name, refs)
    }
}