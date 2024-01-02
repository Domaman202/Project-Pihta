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
import ru.DmN.pht.std.utils.isConstClass
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VTDynamic

object NRFSetB : INodeProcessor<NodeFieldSet> { // todo: calc
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
            else if (type == VTDynamic)
                NRMCall.findMethodOrNull(
                    ctx.global.getType("ru.DmN.pht.std.utils.DynamicUtils", processor.tp),
                    "invokeSetter",
                    node.nodes,
                    processor,
                    ctx
                )?.let {
                    return NodeMCall(
                        info.withType(NodeTypes.MCALL_),
                        NRMCall.processArguments(info, processor, ctx, it.method, listOf(instance, nodeValue(info, node.name)) + node.nodes, it.compression),
                        null,
                        nodeValueClass(info, it.method.declaringClass!!.name),
                        it.method,
                        NodeMCall.Type.VIRTUAL
                    )
                }
            else NRMCall.findMethodOrNull(
                type,
                "set${node.name.let { it[0].toUpperCase() + it.substring(1) }}",
                node.nodes,
                processor,
                ctx
            )
        return if (result == null)
            NodeFSet(
                info.withType(NodeTypes.FSET_),
                mutableListOf(instance, processor.process(node.nodes.first(), ctx, ValType.VALUE)!!),
                node.name,
                if (instance.isConstClass)
                    NodeFSet.Type.STATIC
                else NodeFSet.Type.INSTANCE,
                type
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
}