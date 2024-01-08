package ru.DmN.pht.processors

import ru.DmN.pht.ast.IGetNode
import ru.DmN.pht.std.ast.NodeGetA
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.classes
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRGetC : INodeProcessor<Node> {
    override fun calc(node: Node, processor: Processor, ctx: ProcessingContext): VirtualType {
        node as IGetNode
        return when (node.type) {
            NodeGetA.Type.VARIABLE -> ctx.body[node.name]!!.type()
            NodeGetA.Type.THIS_FIELD -> (ctx.clazz.fields.find { !it.modifiers.isStatic && it.name == node.name } ?: ctx.classes.asSequence().map { it -> it.fields.find { !it.modifiers.isStatic && it.name == node.name } }.first()!!).type
            NodeGetA.Type.THIS_STATIC_FIELD -> (ctx.clazz.fields.find { it.modifiers.isStatic && it.name == node.name } ?: ctx.classes.asSequence().map { it -> it.fields.find { it.modifiers.isStatic && it.name == node.name } }.first()!!).type
        }
    }
}