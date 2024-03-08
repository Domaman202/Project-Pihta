package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeType
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.ctx.with
import ru.DmN.pht.utils.*
import ru.DmN.pht.utils.node.NodeParsedTypes
import ru.DmN.pht.utils.node.processed
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage.TYPES_DEFINE
import ru.DmN.siberia.processor.utils.ProcessingStage.TYPES_PREDEFINE
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.FieldModifiers
import ru.DmN.siberia.utils.vtype.VirtualField.VirtualFieldImpl
import ru.DmN.siberia.utils.vtype.VirtualType.VirtualTypeImpl

object NRClass : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeType {
        val gctx = ctx.global
        //
        val generics = processor.computeListOr(node.nodes[0], ctx)
        val offset = if (generics == null) 0 else 1
        val type =
            VirtualTypeImpl(gctx.name(processor.computeString(node.nodes[offset], ctx)), isFinal = true)
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