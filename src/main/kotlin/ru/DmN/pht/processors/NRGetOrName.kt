package ru.DmN.pht.std.processors

import ru.DmN.pht.ast.NodeTypedGet
import ru.DmN.pht.processors.IAdaptableProcessor
import ru.DmN.pht.std.ast.NodeGetOrName
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.isClass
import ru.DmN.pht.std.utils.lenArgs
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.VirtualType

object NRGetOrName : IStdNodeProcessor<NodeGetOrName>, IAdaptableProcessor<NodeGetOrName> {
    override fun calc(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): VirtualType =
        calc(node.name, ctx)

    fun calc(name: String, ctx: ProcessingContext) =
        if (name == "super")
            ctx.body["this"]!!.type()
        else {
            val variable = ctx.body[name]
            variable?.type()
                ?: if (ctx.isClass())
                    ctx.clazz.fields.find { it.name == name }!!.type
                else throw RuntimeException()
        }

    override fun computeInt(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): Int =
        node.getValueAsString().toInt()

    override fun computeString(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): String =
        node.getValueAsString()

    override fun computeType(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): VirtualType? =
        ctx.body[node.name]!!.type

    override fun computeTypes(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): List<VirtualType> =
        ctx.body.variables
            .asSequence()
            .filter { it.name == node.name }
            .map { it.type() }
            .toList()

    override fun computeGenericType(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): String? =
        if (node.name.endsWith('^'))
            node.name.substring(0, node.name.length - 1)
        else null

    override fun isAdaptableToType(type: VirtualType, node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): Boolean =
        ctx.body.variables.any { lenArgs(it.type, type) >= 0 && it.name == node.name }

    override fun adaptToType(type: VirtualType, node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): NodeGetOrName =
        if (ctx.body.variables.count { it.name == node.name } == 1)
            node
        else NodeTypedGet(node.info.withType(NodeTypes.TYPED_GET_), node.name, type)
}