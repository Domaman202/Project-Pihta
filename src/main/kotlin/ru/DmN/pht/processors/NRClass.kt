package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeType
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.ctx.with
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeListOr
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.utils.node.NodeParsedTypes
import ru.DmN.pht.utils.node.processed
import ru.DmN.pht.utils.type
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage.TYPES_DEFINE
import ru.DmN.siberia.processor.utils.ProcessingStage.TYPES_PREDEFINE
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.FieldModifiers
import ru.DmN.siberia.utils.vtype.VirtualField.VirtualFieldImpl

object NRClass : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeType {
        val gctx = ctx.global
        //
        val generics = processor.computeListOr(node.nodes[0], ctx)
        val offset = if (generics == null) 0 else 1
        val type = PhtVirtualType.Impl(gctx.name(processor.computeString(node.nodes[offset], ctx)), isFinal = true)
        //
        when (node.type) {
            NodeParsedTypes.OBJ -> type.fields += VirtualFieldImpl(
                type,
                "INSTANCE",
                type,
                FieldModifiers(isFinal = true, isStatic = true, isEnum = false)
            )
            NodeParsedTypes.ITF -> type.isInterface = true
        }
        processor.tp.types[type.name.hashCode()] = type
        //
        val new = NodeType(node.info.processed, node.nodes.drop(2 + offset).toMutableList(), type)
        processor.stageManager.pushTask(TYPES_PREDEFINE) {
            val context = ctx.with(type)
            processor.computeList(processor.process(node.nodes[1 + offset], context, true)!!, context)
                .stream()
                .map { processor.computeType(it, context) }
                .forEach { type.parents += it }
            generics?.forEach {
                val generic = processor.computeList(it, ctx)
                type.generics += Pair(processor.computeString(generic[0], ctx), processor.computeType(generic[1], ctx))
            }
            processor.stageManager.pushTask(TYPES_DEFINE) {
                processNodesList(new, processor, context, valMode)
            }
        }
        return new
    }
}