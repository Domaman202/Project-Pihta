package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ProcessingStage
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.utils.VirtualField
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeType
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString

object NRClass : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        if (node.token.text == "obj")
            ctx.global.getType(processor.computeString(node.nodes[0], ctx), processor.tp)
        else null

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeType {
        val gctx = ctx.global
        //
        val type = VirtualType(gctx.name(processor.computeString(processor.process(node.nodes[0], ctx, ValType.VALUE)!!, ctx)))
        when (node.token.text) {
            "obj" -> type.fields += VirtualField(type, "INSTANCE", type, static = true, enum = false)
            "itf" -> type.isInterface = true
        }
        processor.tp.types += type
        //
        val new = NodeType(node.token.processed(), node.nodes.drop(2).toMutableList(), type)
        processor.pushTask(ctx, ProcessingStage.TYPES_PREDEFINE) {
            val context = ctx.with(type)
            type.parents = processor.computeList(processor.process(node.nodes[1], context, ValType.VALUE)!!, context)
                .map { gctx.getType(processor.computeString(it, context), processor.tp) }
                .toMutableList()
            processor.pushTask(context, ProcessingStage.TYPES_DEFINE) {
                NRDefault.process(new, processor, context, mode)
            }
        }
        return new
    }
}