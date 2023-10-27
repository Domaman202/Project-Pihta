package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.std.ast.NodeFSet
import ru.DmN.pht.std.ast.NodeFieldSet
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.computeString

object NUPFSetB : INodeUniversalProcessor<Node, NodeFieldSet> {
    override fun process(node: NodeFieldSet, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeFSet {
        val instance = processor.process(node.instance, ctx, ValType.VALUE)!!
        val type = if (instance.isConstClass()) ctx.global.getType(processor.computeString(instance, ctx), processor.tp) else processor.calc(node.instance, ctx)!!
        return NodeFSet(
            Token(node.token.line, Token.Type.OPERATION, "fset"),
            mutableListOf(instance, processor.process(node.value!!, ctx, ValType.VALUE)!!),
            node.name,
            if (instance.isConstClass())
                NodeFSet.Type.STATIC
            else if (processor.calc(node.instance, ctx)!!.fields.find { it.name == node.name } != null)
                NodeFSet.Type.INSTANCE
            else NodeFSet.Type.UNKNOWN,
            type
        )
    }
}