package ru.DmN.pht.std.processors

import ru.DmN.pht.processor.utils.MethodFindResultB
import ru.DmN.pht.processor.utils.Static
import ru.DmN.pht.std.ast.NodeFGet
import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.nodeValue
import ru.DmN.pht.std.processor.utils.nodeValueClass
import ru.DmN.pht.std.utils.computeType
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VTDynamic
import ru.DmN.siberia.utils.VirtualType

object NRFGetB : INodeProcessor<NodeFMGet> {
    override fun calc(node: NodeFMGet, processor: Processor, ctx: ProcessingContext): VirtualType? =
        getMethod(node, processor, ctx).second?.method?.rettype ?: NRFGetA.findField(getInstanceType(node, processor, ctx), node.name, node.static)?.type

    override fun process(node: NodeFMGet, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE) {
            val info = node.info
            val instance = processor.process(node.instance, ctx, ValType.VALUE)!!
            val result = getMethod(node, processor, ctx)
            if (result.second == null)
                NodeFGet(
                    info.withType(NodeTypes.FGET_),
                    mutableListOf(instance),
                    node.name,
                    result.first.let {
                        val field = NRFGetA.findField(it, node.name, node.static)
                        if (field == null)
                            NodeFGet.Type.UNKNOWN
                        else if (field.modifiers.isStatic)
                            NodeFGet.Type.STATIC
                        else NodeFGet.Type.INSTANCE
                    },
                    result.first
                )
            else {
                val method = result.second!!.method
                if (result.first == VTDynamic)
                    NodeMCall(
                        info.withType(NodeTypes.MCALL_),
                        NRMCall.processArguments(
                            info,
                            processor,
                            ctx,
                            method,
                            listOf(instance, nodeValue(info, node.name)) + node.nodes,
                            result.second!!.compression
                        ),
                        null,
                        nodeValueClass(info, method.declaringClass!!.name),
                        method,
                        NodeMCall.Type.VIRTUAL
                    )
                else NodeMCall(
                    info.withType(NodeTypes.MCALL_),
                    NRMCall.processArguments(info, processor, ctx, method, node.nodes, result.second!!.compression),
                    null,
                    instance,
                    method,
                    NodeMCall.Type.VIRTUAL
                )
            }
        } else null

    private fun getMethod(node: NodeFMGet, processor: Processor, ctx: ProcessingContext): Pair<VirtualType, MethodFindResultB?> {
        val type = getInstanceType(node, processor, ctx)!!
        return if (node.native)
            Pair(type, null)
        else {
            val result =
                if (type == VTDynamic)
                    NRMCall.findMethod(
                        ctx.global.getType("ru.DmN.pht.std.utils.DynamicUtils", processor.tp),
                        "invokeGetter",
                        node.nodes,
                        Static.ANY,
                        processor,
                        ctx
                    )
                else findGetter(type, node.name, if (node.static) Static.STATIC else Static.NO_STATIC, processor, ctx)
            Pair(type, result)
        }
    }

    fun findGetter(type: VirtualType, name: String, static: Static, processor: Processor, ctx: ProcessingContext) =
        NRMCall.findMethodOrNull(
            type,
            "get${name.let { it[0].toUpperCase() + it.substring(1) }}",
            emptyList(),
            static,
            processor,
            ctx
        )

    private fun getInstanceType(node: NodeFMGet, processor: Processor, ctx: ProcessingContext) =
        if (node.static)
            processor.computeType(node.instance, ctx)
        else processor.calc(node.instance, ctx)
}