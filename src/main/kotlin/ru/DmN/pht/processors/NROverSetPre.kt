package ru.DmN.pht.processors

import ru.DmN.pht.processor.utils.compute
import ru.DmN.pht.processor.utils.computeInt
import ru.DmN.pht.processor.utils.processAsGetter
import ru.DmN.pht.processor.utils.processAsSetterLazy
import ru.DmN.pht.utils.node.nodeBody
import ru.DmN.pht.utils.node.nodeDef
import ru.DmN.pht.utils.node.nodeGetVariable
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NROverSetPre : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.calc(processGetter(node.nodes[0], processor, ctx), ctx)!!

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val info = node.info
        val type = calc(node, processor, ctx)
        val tmp = Variable.tmp(node)
        val body = ArrayList<Node>()
        val offset = node.nodes.size - 2
        body += nodeDef(
            info,
            tmp,
            (processor.compute(node.nodes[1 + offset].copy(), ctx) as INodesList).apply {
                nodes.add(if (offset == 1) processor.computeInt(node.nodes[1], ctx) else nodes.size, processGetter(node.nodes[0], processor, ctx))
            }
        )
        body += processSetter(node.nodes[0], nodeGetVariable(info, tmp, type), processor, ctx)
        if (valMode)
            body += nodeGetVariable(info, tmp, type)
        return processor.process(nodeBody(info, body), ctx, valMode)
    }

    @Suppress("NOTHING_TO_INLINE")
    inline fun processSetter(node: Node, value: Node, processor: Processor, ctx: ProcessingContext): Node =
        processor.processAsSetterLazy(node, listOf(value), ctx, false)!!

    @Suppress("NOTHING_TO_INLINE")
    inline fun processGetter(node: Node, processor: Processor, ctx: ProcessingContext): Node =
        processor.processAsGetter(node, ctx)!!
}