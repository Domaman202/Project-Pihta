package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeType
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeListOr
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.computeType
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.siberia.utils.VirtualField.VirtualFieldImpl
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.VirtualType.Companion.ofKlass
import ru.DmN.siberia.utils.VirtualType.VirtualTypeImpl
import ru.DmN.siberia.utils.text

object NRClass : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        if (node.token.text == "obj")
            processor.computeType(node.nodes[0], ctx)
        else null

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeType {
        val gctx = ctx.global
        //
        val generics = processor.computeListOr(node.nodes[0], ctx)
        val offset = if (generics == null) 0 else 1
        val type = VirtualTypeImpl(gctx.name(processor.computeString(node.nodes[offset], ctx)))
        //
        when (node.text) {
            "obj" -> type.fields += VirtualFieldImpl(type, "INSTANCE", type, isStatic = true, isEnum = false)
            "itf" -> type.isInterface = true
        }
        processor.tp.types += type
        //
        val new = NodeType(node.token.processed(), node.nodes.drop(2 + offset).toMutableList(), type)
        processor.stageManager.pushTask(ProcessingStage.TYPES_PREDEFINE) {
            val context = ctx.with(type)
            processor.computeList(processor.process(node.nodes[1 + offset], context, ValType.VALUE)!!, context)
                .map { processor.computeType(it, context) }
                .forEach { type.parents += it }
            generics?.forEach {
                val generic = processor.computeList(it, ctx)
                type.generics += Pair(processor.computeString(generic[0], ctx), processor.computeType(generic[1], ctx))
            }
            processor.stageManager.pushTask(ProcessingStage.TYPES_DEFINE) {
                NRDefault.process(new, processor, context, mode)
            }
        }
        return new
    }
}