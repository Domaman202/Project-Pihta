package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeGet
import ru.DmN.pht.ast.NodeGet.Type.*
import ru.DmN.pht.processor.ctx.body
import ru.DmN.pht.processor.ctx.classes
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.exception.MessageException
import ru.DmN.siberia.utils.vtype.VirtualType

object NRGetC : INodeProcessor<NodeGet> {
    override fun calc(node: NodeGet, processor: Processor, ctx: ProcessingContext): VirtualType =
        when (node.type) {
            VARIABLE -> ctx.body[node.name]?.type ?: throw MessageException(null, "Переменная '${node.name}' не найдена!")
            THIS_FIELD -> ctx.classes.firstNotNullOf { it -> it.fields.find { !it.modifiers.isStatic && it.name == node.name } }.type
            THIS_STATIC_FIELD -> ctx.classes.firstNotNullOf { it -> it.fields.find { it.modifiers.isStatic && it.name == node.name } }.type
        }
}