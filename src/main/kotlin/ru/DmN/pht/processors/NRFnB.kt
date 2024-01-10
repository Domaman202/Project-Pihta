package ru.DmN.pht.std.processors

import ru.DmN.pht.processors.IAdaptableProcessor
import ru.DmN.pht.std.ast.NodeFn
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.findLambdaMethod
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.VirtualType

object NRFnB : IStdNodeProcessor<NodeFn>, IAdaptableProcessor<NodeFn> {
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
}