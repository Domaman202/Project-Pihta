package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeIncDec
import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.ast.NodeMCall.Type.EXTEND
import ru.DmN.pht.node.NodeTypes.MCALL_
import ru.DmN.pht.node.nodeValueClass
import ru.DmN.pht.node.processed
import ru.DmN.pht.utils.computeString
import ru.DmN.pht.utils.processNodes
import ru.DmN.pht.utils.text
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.VALUE
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRIncDec : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRMath.calc(node, processor, ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val nodes = processor.processNodes(node, ctx, VALUE)
        val info = node.info
        val result = NRMath.getExtend(processor.calc(nodes[0], ctx)!!, node.text, nodes.drop(1), processor, ctx)
        return if (result == null)
            NodeIncDec(
                info.processed,
                processor.computeString(node.nodes[0], ctx)
            )
        else NodeMCall(
            info.withType(MCALL_),
            NRMath.processArguments(info, processor, ctx, listOf(nodes[0]) + result.args, result),
            null,
            nodeValueClass(info, result.method.declaringClass!!.name),
            result.method,
            EXTEND
        )
    }
}