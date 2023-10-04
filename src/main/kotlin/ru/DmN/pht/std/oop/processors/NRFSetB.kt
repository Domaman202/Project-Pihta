package ru.DmN.pht.std.oop.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.processors.INodeProcessor
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.std.fp.ast.NodeFSet
import ru.DmN.pht.std.fp.ast.NodeFieldSet

object NRFSetB : INodeProcessor<NodeFieldSet> {
    override fun process(node: NodeFieldSet, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeFSet {
        val instance = processor.process(node.instance, ctx, ValType.VALUE)!!
        return NodeFSet(
            Token(node.tkOperation.line, Token.Type.OPERATION, "fset"),
            mutableListOf(instance, processor.process(node.value!!, ctx, ValType.VALUE)!!),
            node.name,
            if (instance.isConstClass())
                NodeFSet.Type.STATIC
            else if (processor.calc(node.instance, ctx)!!.fields.find { it.name == node.name } != null)
                NodeFSet.Type.INSTANCE
            else NodeFSet.Type.UNKNOWN
        )
    }
}