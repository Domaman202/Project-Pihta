package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeIncDec
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.processor.utils.nodeClass
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.processNodes
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.line
import ru.DmN.siberia.utils.text

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