package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualField.VirtualFieldImpl
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.VirtualType.VirtualTypeImpl
import ru.DmN.pht.std.ast.NodeRFn
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.utils.LazyProcessValueList
import ru.DmN.pht.std.utils.NVC
import ru.DmN.pht.std.utils.computeStringNodes
import kotlin.math.absoluteValue

object NRRFn : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("Any", processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeRFn {
        val gctx = ctx.global
        val cctx = ctx.clazz
        val bctx = ctx.body
        val fakeType = VirtualTypeImpl(gctx.name("PhtLambda${node.hashCode().absoluteValue}"))
        val context = ctx.with(BodyContext.of(null))
        val nodes = LazyProcessValueList(node, processor, context)
        val offset = if (nodes[0].isConstClass()) 1 else 0
        val type = if (offset == 1) gctx.getType(nodes[0].getValueAsString(), processor.tp) else null
        val refs = processor.computeStringNodes(nodes[offset], context)
            .map { ref -> bctx[ref]?.let { NVC.of(it) } ?: NVC.of(cctx.fields.find { it.name == ref }!!) }
        refs.forEach { fakeType.fields += VirtualFieldImpl(fakeType, it.name, it.type, isStatic = false, isEnum = false) }
        val args = processor.computeStringNodes(nodes[offset + 1], context)
        val body = nodes.dropAndProcess(offset + 2).toMutableList()
        return NodeRFn(node.token.processed(), body, type, args, fakeType.name, refs)
    }
}