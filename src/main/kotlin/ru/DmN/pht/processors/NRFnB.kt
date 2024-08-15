package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeFn
import ru.DmN.pht.ast.NodeInlBodyA
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.utils.findLambdaMethod
import ru.DmN.pht.utils.node.NodeTypes.INL_BODY_A
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType

object NRFnB : IComputableProcessor<NodeFn>, IAdaptableProcessor<NodeFn>, IInlinableProcessor<NodeFn> {
    override fun calc(node: NodeFn, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type ?: ctx.global.getType("Any")

    override fun adaptableTo(node: NodeFn, type: VirtualType, processor: Processor, ctx: ProcessingContext): Int =
        if (node.type == null)
            if (findLambdaMethod(type).argsn.size == node.args.size) 1
            else -1
        else if (node.type!!.isAssignableFrom(type)) 1
        else -1

    override fun adaptToType(node: NodeFn, type: VirtualType, processor: Processor, ctx: ProcessingContext): NodeFn {
        if (node.type?.isAssignableFrom(type) != true)
            node.type = type
        return node
    }

    override fun isInlinable(node: NodeFn, processor: Processor, ctx: ProcessingContext): Boolean =
        true

    override fun inline(node: NodeFn, processor: Processor, ctx: ProcessingContext): Pair<List<String>, Node> =
        Pair(node.args, NodeInlBodyA(node.info.withType(INL_BODY_A), node.source.toMutableList(), calc(node, processor, ctx)))
}