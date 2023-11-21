package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeCompare
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.processor.utils.nodeClass
import ru.DmN.pht.std.utils.line
import ru.DmN.pht.std.utils.processNodes
import ru.DmN.pht.std.utils.text

object NRCompare : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        NRMath.findExtend(processor.calc(node.nodes[0], ctx)!!, node.text, node.nodes.drop(1), processor, ctx)?.rettype ?: VirtualType.BOOLEAN

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val nodes = processor.processNodes(node, ctx, ValType.VALUE)
        val firstType = processor.calc(nodes[0], ctx)!!
        val result = NRMath.getExtend(firstType, node.text, nodes.drop(1), processor, ctx)
        return if (result == null) {
            if (mode == ValType.VALUE)
                NRDefault.processValue(NodeCompare(node.token.processed(), node.nodes), processor, ctx)
            else null
        } else {
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