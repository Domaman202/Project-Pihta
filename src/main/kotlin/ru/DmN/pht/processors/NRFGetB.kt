package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeFGet
import ru.DmN.pht.ast.NodeFGet.Type.*
import ru.DmN.pht.ast.NodeFMGet
import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.ast.NodeMCall.Type.VIRTUAL
import ru.DmN.pht.processor.utils.MethodFindResultB
import ru.DmN.pht.processor.utils.Static
import ru.DmN.pht.processor.utils.global
import ru.DmN.pht.utils.computeType
import ru.DmN.pht.utils.node.NodeTypes.FGET_
import ru.DmN.pht.utils.node.NodeTypes.MCALL_
import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.pht.utils.node.nodeValueClass
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VTDynamic
import ru.DmN.siberia.utils.vtype.VirtualType

object NRFGetB : INodeProcessor<NodeFMGet> {
    override fun calc(node: NodeFMGet, processor: Processor, ctx: ProcessingContext): VirtualType? =
        getMethod(node, processor, ctx).second?.method?.rettype ?: NRFGetA.findField(getInstanceType(node, processor, ctx), node.name, node.static)?.type

    override fun process(node: NodeFMGet, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        if (valMode) {
            val info = node.info
            val instance = processor.process(node.instance, ctx, true)!!
            val result = getMethod(node, processor, ctx)
            if (result.second == null)
                NodeFGet(
                    info.withType(FGET_),
                    mutableListOf(instance),
                    node.name,
                    result.first.let {
                        val field = NRFGetA.findField(it, node.name, node.static)
                        if (field == null)
                            UNKNOWN
                        else if (field.modifiers.isStatic)
                            STATIC
                        else INSTANCE
                    },
                    result.first
                )
            else {
                val method = result.second!!.method
                if (result.first == VTDynamic)
                    NodeMCall(
                        info.withType(MCALL_),
                        NRMCall.processArguments(
                            info,
                            processor,
                            ctx,
                            method,
                            listOf(instance, nodeValue(info, node.name)) + node.nodes,
                            result.second!!.compression
                        ),
                        null,
                        nodeValueClass(info, method.declaringClass.name),
                        method,
                        VIRTUAL
                    )
                else NodeMCall(
                    info.withType(MCALL_),
                    NRMCall.processArguments(info, processor, ctx, method, node.nodes, result.second!!.compression),
                    null,
                    instance,
                    method,
                    VIRTUAL
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
                        ctx.global.getType("ru.DmN.pht.utils.DynamicUtils", processor.tp),
                        "invokeGetter",
                        node.nodes,
                        Static.ANY,
                        processor,
                        ctx
                    )
                else findGetter(type, node.name, node.nodes, if (node.static) Static.STATIC else Static.NO_STATIC, processor, ctx)
            Pair(type, result)
        }
    }

    fun findGetter(type: VirtualType, name: String, nodes: List<Node>, static: Static, processor: Processor, ctx: ProcessingContext) =
        NRMCall.findMethodOrNull(
            type,
            "get${name.let { it[0].uppercase() + it.substring(1) }}",
            nodes,
            static,
            processor,
            ctx
        )

    private fun getInstanceType(node: NodeFMGet, processor: Processor, ctx: ProcessingContext) =
        if (node.static)
            processor.computeType(node.instance, ctx)
        else processor.calc(node.instance, ctx)
}