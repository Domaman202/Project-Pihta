package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeGet
import ru.DmN.pht.ast.NodeGet.Type.*
import ru.DmN.pht.processor.utils.body
import ru.DmN.pht.processor.utils.classes
import ru.DmN.pht.processor.utils.clazz
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRGetC : INodeProcessor<NodeGet> {
    override fun calc(node: NodeGet, processor: Processor, ctx: ProcessingContext): VirtualType =
        when (node.type) {
            VARIABLE -> ctx.body[node.name]!!.type()
            THIS_FIELD -> (ctx.clazz.fields.find { !it.modifiers.isStatic && it.name == node.name } ?: ctx.classes.asSequence().map { it -> it.fields.find { !it.modifiers.isStatic && it.name == node.name } }.first()!!).type
            THIS_STATIC_FIELD -> (ctx.clazz.fields.find { it.modifiers.isStatic && it.name == node.name } ?: ctx.classes.asSequence().map { it -> it.fields.find { it.modifiers.isStatic && it.name == node.name } }.first()!!).type
        }
}