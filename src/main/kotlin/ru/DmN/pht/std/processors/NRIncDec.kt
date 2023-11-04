package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeIncDec
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.processor.utils.nodeClass
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.line
import ru.DmN.pht.std.utils.processNodes
import ru.DmN.pht.std.utils.text

object NRIncDec : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRMath.findExtend(processor.calc(node.nodes[0], ctx)!!, node.text, node.nodes.drop(1), processor, ctx)?.rettype

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val nodes = processor.processNodes(node, ctx, ValType.VALUE)
        val firstType = processor.calc(nodes[0], ctx)!!
        val result = NRMath.getExtend(firstType, node.text, nodes.drop(1), processor, ctx)
        return if (result == null)
            NodeIncDec(node.token.processed(), processor.computeString(node.nodes[0], ctx))
        else {
            val line = node.line
            NodeMCall(
                Token.operation(line, "!mcall"),
                NRMCall.processArguments(line, processor, ctx, result.second, listOf(nodes[0]) + result.first),
                nodeClass(line, result.second.declaringClass!!.name),
                result.second,
                NodeMCall.Type.EXTEND
            )
        }
    }
}