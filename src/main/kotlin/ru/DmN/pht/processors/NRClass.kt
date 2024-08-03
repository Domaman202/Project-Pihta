package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeType
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.ctx.with
import ru.DmN.pht.processor.utils.PhtProcessingStage.TYPES_DEFINE
import ru.DmN.pht.processor.utils.PhtProcessingStage.TYPES_PREDEFINE
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeListOr
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.utils.node.NodeParsedTypes
import ru.DmN.pht.utils.node.processed
import ru.DmN.pht.utils.type
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.pht.utils.vtype.VVTWithGenerics
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.exception.pushOrRunTask
import ru.DmN.siberia.utils.vtype.FieldModifiers
import ru.DmN.siberia.utils.vtype.VirtualField.VirtualFieldImpl

object NRClass : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeType {
        val gctx = ctx.global
        //
        val generics = processor.computeListOr(node.nodes[0], ctx)
        val offset = if (generics == null) 0 else 1
        val name = gctx.name(processor.computeString(node.nodes[offset], ctx))
        val type = PhtVirtualType.Impl(name, isFinal = true)
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
        processor.tp.types[name.hashCode()] = type
        //
        val new = NodeType(node.info.processed, node.nodes.drop(2 + offset).toMutableList(), type)
        processor.pushOrRunTask(TYPES_PREDEFINE, node) {
            generics?.forEach {
                val pair = processor.computeList(it, ctx)
                val genericName = "${processor.computeString(pair[0], ctx)}$$name"
                type.genericsAccept += genericName
                type.genericsDefine[genericName] = processor.computeType(pair[1], ctx)
            }
            //
            processor.computeList(node.nodes[1 + offset], ctx).forEach { it ->
                type.parents += processor.computeType(it, ctx).apply {
                    if (this is VVTWithGenerics) {
                        this.genericsData.forEach {
                            val value = it.value
                            if (value.isFirst)
                                type.genericsDefine[it.key] = value.first()
                            else type.genericsMap[it.key] = "${value.second()}$$name"
                        }
                    }
                }
            }
            //
            processor.pushOrRunTask(TYPES_DEFINE, node) {
                processNodesList(new, processor, ctx.with(type), valMode)
            }
        }
        return new
    }
}