package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeFSet
import ru.DmN.pht.std.ast.NodeFieldSet
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.nodeValue
import ru.DmN.pht.std.processor.utils.nodeValueClass
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VTDynamic
import ru.DmN.siberia.utils.VirtualType

object NRFSetB : INodeProcessor<NodeFieldSet> {
    override fun process(node: NodeFieldSet, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val info = node.info
        val instance = processor.process(node.instance, ctx, ValType.VALUE)!!
            .let { if (node.static) nodeValueClass(info, processor.computeString(it, ctx)) else it }
        val type =
            if (node.static)
                ctx.global.getType((instance as NodeValue).value, processor.tp)
            else processor.calc(node.instance, ctx)!!
        val result =
            if (node.native)
                null
            else if (type == VTDynamic) {
                val result = NRMCall.findMethod(
                    ctx.global.getType("ru.DmN.pht.std.utils.DynamicUtils", processor.tp),
                    "invokeSetter",
                    node.nodes,
                    processor,
                    ctx
                )
                return NodeMCall(
                    info.withType(NodeTypes.MCALL_),
                    NRMCall.processArguments(
                        info,
                        processor,
                        ctx,
                        result.method,
                        listOf(instance, nodeValue(info, node.name)) + node.nodes,
                        result.compression
                    ),
                    null,
                    nodeValueClass(info, result.method.declaringClass!!.name),
                    result.method,
                    NodeMCall.Type.VIRTUAL
                )
            } else findSetter(type, node.name, node.nodes, processor, ctx)
        return if (result == null)
            NodeFSet(
                info.withType(NodeTypes.FSET_),
                mutableListOf(instance, processor.process(node.nodes.first(), ctx, ValType.VALUE)!!),
                type.fields.find { it.name == node.name }!!
            )
        else NodeMCall(
            info.withType(NodeTypes.MCALL_),
            NRMCall.processArguments(info, processor, ctx, result.method, node.nodes, result.compression),
            null,
            instance,
            result.method,
            NodeMCall.Type.VIRTUAL
        )
    }

    fun findSetter(type: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext) =
        NRMCall.findMethodOrNull(
            type,
            "set${name.let { it[0].toUpperCase() + it.substring(1) }}",
            args,
            processor,
            ctx
        )
}