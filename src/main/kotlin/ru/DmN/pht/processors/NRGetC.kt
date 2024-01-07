package ru.DmN.pht.processors

import ru.DmN.pht.std.ast.NodeGet
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRGetC : INodeProcessor<NodeGet> {
    override fun calc(node: NodeGet, processor: Processor, ctx: ProcessingContext): VirtualType? =
        when (node.type) {
            NodeGet.Type.VARIABLE -> ctx.body[node.name]!!.type()
            NodeGet.Type.THIS_FIELD -> ctx.clazz.fields.find { !it.modifiers.isStatic && it.name == node.name }!!.type
            NodeGet.Type.THIS_STATIC_FIELD -> ctx.clazz.fields.find { it.modifiers.isStatic && it.name == node.name }!!.type
        }
}