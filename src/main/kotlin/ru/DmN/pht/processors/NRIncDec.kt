package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.ast.NodeMCall.Type.EXTEND
import ru.DmN.pht.processor.utils.processNodes
import ru.DmN.pht.utils.node.NodeParsedTypes.ADD
import ru.DmN.pht.utils.node.NodeParsedTypes.SUB
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.pht.utils.node.nodeOverSetLeft
import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.pht.utils.node.nodeValueClass
import ru.DmN.pht.utils.text
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRIncDec : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val nodes = processor.processNodes(node, ctx, true)
        val info = node.info
        val result = NRMath.getExtend(processor.calc(nodes[0], ctx)!!, node.text, nodes.drop(1), processor, ctx)
        return if (result == null)
            processor.process(
                when (info.type) {
                    DEC_PRE  -> nodeOverSetLeft(info, node.nodes[0], NodeNodesList(info.withType(SUB), mutableListOf(nodeValue(info, 1))))
                    DEC_POST -> nodeOverSetLeft(info, node.nodes[0], NodeNodesList(info.withType(SUB), mutableListOf(nodeValue(info, 1))))
                    INC_PRE  -> nodeOverSetLeft(info, node.nodes[0], NodeNodesList(info.withType(ADD), mutableListOf(nodeValue(info, 1))))
                    INC_POST -> nodeOverSetLeft(info, node.nodes[0], NodeNodesList(info.withType(ADD), mutableListOf(nodeValue(info, 1))))
                    else -> throw UnsupportedOperationException()
                },
                ctx,
                valMode
            )
        else NodeMCall(
            info.withType(MCALL_),
            NRMath.processArguments(info, processor, ctx, listOf(nodes[0]) + result.args, result),
            null,
            nodeValueClass(info, result.method.declaringClass.name),
            result.method,
            EXTEND
        )
    }
}