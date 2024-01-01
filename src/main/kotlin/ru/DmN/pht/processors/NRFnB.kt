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
        node.type ?: ctx.global.getType("Any", processor.tp)

    override fun isAdaptableToType(type: VirtualType, node: NodeFn, processor: Processor, ctx: ProcessingContext): Boolean =
        if (node.type == null)
            findLambdaMethod(type).argsn.size == node.args.size
        else node.type!!.isAssignableFrom(type)

    override fun adaptToType(type: VirtualType, node: NodeFn, processor: Processor, ctx: ProcessingContext): NodeFn {
        node.type = type
        return node
    }
}