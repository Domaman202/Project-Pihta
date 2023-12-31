package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeCompare
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.node.nodeValueClass
import ru.DmN.pht.std.node.processed
import ru.DmN.pht.std.processor.utils.processValue
import ru.DmN.pht.std.utils.processNodes
import ru.DmN.pht.std.utils.text
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRCompare : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        NRMath.findExtend(processor.calc(node.nodes[0], ctx)!!, node.text, node.nodes.drop(1), processor, ctx)?.first?.rettype ?: VirtualType.BOOLEAN

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val nodes = processor.processNodes(node, ctx, ValType.VALUE)
        val firstType = processor.calc(nodes[0], ctx)!!
        val result = NRMath.getExtend(firstType, node.text, nodes.drop(1), processor, ctx)
        return if (result == null)
            if (mode == ValType.VALUE)
                processValue(NodeCompare(node.info.processed, node.nodes), processor, ctx)
            else null
        else {
            val info = node.info
            NodeMCall(
                info.withType(NodeTypes.MCALL_),
                NRMath.processArguments(info, processor, ctx, listOf(nodes[0]) + result.args, result),
                null,
                nodeValueClass(info, result.method.declaringClass!!.name),
                result.method,
                NodeMCall.Type.EXTEND
            )
        }
    }
}