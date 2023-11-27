package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.INodesList
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualField.VirtualFieldImpl
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.VirtualType.VirtualTypeImpl
import ru.DmN.pht.std.ast.NodeFn
import ru.DmN.pht.std.processor.ctx.BodyContext
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.utils.*
import kotlin.math.absoluteValue

object NRFn : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType(if (node.nodes[0].isConstClass) processor.computeString(node.nodes[0], ctx) else "Any", processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeFn {
        val gctx = ctx.global
        val cctx = ctx.clazz
        val bctx = ctx.body
        val fakeType = VirtualTypeImpl(gctx.name("PhtLambda${node.hashCode().absoluteValue}"))
        val context = ctx.with(BodyContext.of(null))
        val nodes = LazyProcessValueList(node, processor, context)
        val offset = if (nodes[0].isConstClass) 1 else 0
        val type = if (offset == 1) gctx.getType(nodes[0].valueAsString, processor.tp) else null
        val refs = processor.computeStringNodes(nodes[offset] as INodesList, context)
            .map { ref -> bctx[ref]?.let { NVC.of(it) } ?: NVC.of(cctx.fields.find { it.name == ref }!!) }
        refs.forEach { fakeType.fields += VirtualFieldImpl(fakeType, it.name, it.type, isStatic = false, isEnum = false) }
        val args = processor.computeStringNodes(nodes[offset + 1] as INodesList, context)
        val body = nodes.dropAndProcess(offset + 2).toMutableList()
        return NodeFn(node.token.processed(), body, type, args, fakeType.name, refs)
    }
}