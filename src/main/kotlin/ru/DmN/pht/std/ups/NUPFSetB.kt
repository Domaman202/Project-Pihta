package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.std.ast.NodeFSet
import ru.DmN.pht.std.ast.NodeFieldSet
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.processors.NRMCall
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.line

object NUPFSetB : INodeUniversalProcessor<Node, NodeFieldSet> {
    override fun process(node: NodeFieldSet, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val instance = processor.process(node.instance, ctx, ValType.VALUE)!!
        val type = if (instance.isConstClass())
            ctx.global.getType(processor.computeString(instance, ctx), processor.tp)
        else processor.calc(node.instance, ctx)!!
        val result = NRMCall.findMethodOrNull(
            type,
            "set${node.name.let { it[0].toUpperCase() + it.substring(1) }}",
            listOf(node.value!!),
            processor,
            ctx
        )
        return if (result == null)
            NodeFSet(
                Token(node.line, Token.Type.OPERATION, "fset"),
                mutableListOf(instance, processor.process(node.value!!, ctx, ValType.VALUE)!!),
                node.name,
                if (instance.isConstClass())
                    NodeFSet.Type.STATIC
                else NodeFSet.Type.INSTANCE,
                type
            )
        else NodeMCall(
            Token.operation(node.line, "!mcall"),
            NRMCall.processArguments(node.line, processor, ctx, result.second, listOf(node.value!!)),
            instance,
            result.second,
            NodeMCall.Type.VIRTUAL
        )
    }
}