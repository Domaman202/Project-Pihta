package ru.DmN.pht.processors

import ru.DmN.pht.processor.utils.Static
import ru.DmN.pht.ast.NodeFSet
import ru.DmN.pht.ast.NodeFieldSet
import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.ast.NodeValue
import ru.DmN.pht.node.NodeTypes
import ru.DmN.pht.node.nodeValue
import ru.DmN.pht.node.nodeValueClass
import ru.DmN.pht.processor.utils.global
import ru.DmN.pht.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.VALUE
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VTDynamic
import ru.DmN.siberia.utils.VirtualType

object NRFSetB : INodeProcessor<NodeFieldSet> {
    override fun process(node: NodeFieldSet, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val info = node.info
        val instance = processor.process(node.instance, ctx, VALUE)!!
            .let {
                if (node.static)
                    nodeValueClass(info, processor.computeString(it, ctx))
                else it
            }
        val type =
            if (node.static)
                ctx.global.getType((instance as NodeValue).value, processor.tp)
            else processor.calc(node.instance, ctx)!!
        val result =
            if (node.native)
                null
            else if (type == VTDynamic) {
                val result = NRMCall.findMethod(
                    ctx.global.getType("ru.DmN.pht.utils.DynamicUtils", processor.tp),
                    "invokeSetter",
                    node.nodes,
                    Static.ANY,
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
                    nodeValueClass(info, result.method.declaringClass.name),
                    result.method,
                    NodeMCall.Type.VIRTUAL
                )
            } else findSetter(type, node.name, node.nodes, if (node.static) Static.STATIC else Static.NO_STATIC, processor, ctx)
        return if (result == null)
            NodeFSet(
                info.withType(NodeTypes.FSET_),
                mutableListOf(instance, processor.process(node.nodes.first(), ctx, VALUE)!!),
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

    fun findSetter(type: VirtualType, name: String, args: List<Node>, static: Static, processor: Processor, ctx: ProcessingContext) =
        NRMCall.findMethodOrNull(
            type,
            "set${name.let { it[0].toUpperCase() + it.substring(1) }}",
            args,
            static,
            processor,
            ctx
        )
}