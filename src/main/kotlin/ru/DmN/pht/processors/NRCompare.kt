package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeCompare
import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.ast.NodeMCall.Type.EXTEND
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.utils.processNodes
import ru.DmN.pht.processor.utils.processValue
import ru.DmN.pht.utils.node.NodeTypes.MCALL_
import ru.DmN.pht.utils.node.nodeAs
import ru.DmN.pht.utils.node.nodeValueClass
import ru.DmN.pht.utils.node.processed
import ru.DmN.pht.utils.text
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.mapMutable
import ru.DmN.siberia.utils.vtype.VirtualType

object NRCompare : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        NRMath.findExtend(processor.calc(node.nodes[0], ctx)!!, node.text, node.nodes.drop(1), processor, ctx)?.first?.rettype ?: ctx.global.getType("boolean")

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        process(node, { _, nodes -> NodeCompare(node.info.processed, nodes) }, processor, ctx, valMode)

    fun process(node: NodeNodesList, ctor: (type: VirtualType, nodes: MutableList<Node>) -> NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val nodes = processor.processNodes(node, ctx, true)
        val firstType = processor.calc(nodes[0], ctx)!!
        val result = NRMath.getExtend(firstType, node.text, nodes.drop(1), processor, ctx)
        val info = node.info
        return if (result == null)
            if (valMode)
                ctor(firstType, nodes.mapMutable { processor.process(nodeAs(info, it, firstType.name), ctx, true)!! })
            else null
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