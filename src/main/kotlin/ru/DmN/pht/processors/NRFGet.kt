package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeFGet
import ru.DmN.pht.ast.NodeFGet.Type.*
import ru.DmN.pht.utils.computeType
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualField
import ru.DmN.siberia.utils.vtype.VirtualType

object NRFGet : INodeProcessor<NodeFGet> {
    override fun calc(node: NodeFGet, processor: Processor, ctx: ProcessingContext): VirtualType {
        val filter = when (node.type) {
            UNKNOWN  -> { _:  VirtualField -> true }
            STATIC   -> { it: VirtualField -> it.modifiers.isStatic }
            INSTANCE -> { it: VirtualField -> !it.modifiers.isStatic }
        }
        return (if (node.type == STATIC) processor.computeType(node.nodes[0], ctx) else processor.calc(node.nodes[0], ctx)!!)
            .fields
            .asSequence()
            .filter{ it.name == node.name && filter(it) }
            .first()
            .type
    }
}