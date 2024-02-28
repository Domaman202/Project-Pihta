package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeFn
import ru.DmN.pht.ast.NodeInlBodyA
import ru.DmN.pht.processor.utils.global
import ru.DmN.pht.utils.findLambdaMethod
import ru.DmN.pht.utils.node.NodeTypes.INL_BODY_A
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType

object NRFnB : IStdNodeProcessor<NodeFn>, IAdaptableProcessor<NodeFn>, IInlinableProcessor<NodeFn> {
    override fun calc(node: NodeFn, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.source.type ?: ctx.global.getType("Any", processor.tp)

    override fun adaptableTo(type: VirtualType, node: NodeFn, processor: Processor, ctx: ProcessingContext): Int =
        if (node.source.type == null)
            if (findLambdaMethod(type).argsn.size == node.source.args.size) 1
            else -1
        else if (node.source.type!!.isAssignableFrom(type)) 1
        else -1

    override fun adaptToType(type: VirtualType, node: NodeFn, processor: Processor, ctx: ProcessingContext): NodeFn {
        node.source.type = type
        return node
    }

    override fun isInlinable(node: NodeFn, processor: Processor, ctx: ProcessingContext): Boolean =
        true

    override fun inline(node: NodeFn, processor: Processor, ctx: ProcessingContext): Node =
        NodeInlBodyA(node.info.withType(INL_BODY_A), node.source.nodes.toMutableList(), calc(node, processor, ctx))
}