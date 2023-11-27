package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.std.ast.NodeFSet
import ru.DmN.pht.std.ast.NodeFieldSet
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.nodeClass
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.processors.NRMCall
import ru.DmN.pht.std.utils.VTDynamic
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.isConstClass
import ru.DmN.pht.std.utils.line

object NUPFSetB : INodeUniversalProcessor<Node, NodeFieldSet> {
    override fun process(node: NodeFieldSet, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val instance = processor.process(node.instance, ctx, ValType.VALUE)!!
            .let { if (node.static) nodeClass(node.line, processor.computeString(it, ctx)) else it }
        val type =
            if (node.static)
                ctx.global.getType((instance as NodeValue).value, processor.tp)
            else processor.calc(node.instance, ctx)!!
        val result =
            if (node.native)
                null
            else if (type == VTDynamic) {
                NRMCall.findMethodOrNull(
                    ctx.global.getType("ru.DmN.pht.std.utils.DynamicUtils", processor.tp),
                    "invokeSetter",
                    node.nodes,
                    processor,
                    ctx
                )?.let {
                    return NodeMCall(
                        Token.operation(node.line, "!mcall"),
                        NRMCall.processArguments(node.line, processor, ctx, it.second, listOf(instance) + node.nodes),
                        nodeClass(node.line, it.second.declaringClass!!.name),
                        it.second,
                        NodeMCall.Type.VIRTUAL
                    )
                }
            } else NRMCall.findMethodOrNull(
                type,
                "set${node.name.let { it[0].toUpperCase() + it.substring(1) }}",
                node.nodes,
                processor,
                ctx
            )
        return if (result == null)
            NodeFSet(
                Token(node.line, Token.Type.OPERATION, "fset"),
                mutableListOf(instance, processor.process(node.nodes.first(), ctx, ValType.VALUE)!!),
                node.name,
                if (instance.isConstClass)
                    NodeFSet.Type.STATIC
                else NodeFSet.Type.INSTANCE,
                type
            )
        else NodeMCall(
            Token.operation(node.line, "!mcall"),
            NRMCall.processArguments(node.line, processor, ctx, result.second, node.nodes),
            instance,
            result.second,
            NodeMCall.Type.VIRTUAL
        )
    }
}