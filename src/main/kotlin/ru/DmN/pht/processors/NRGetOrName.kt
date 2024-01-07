package ru.DmN.pht.std.processors

import ru.DmN.pht.ast.NodeTypedGet
import ru.DmN.pht.processors.IAdaptableProcessor
import ru.DmN.pht.std.ast.NodeGetOrName
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.utils.lenArgs
import ru.DmN.siberia.Processor
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.VirtualType

object NRGetOrName : IStdNodeProcessor<NodeGetOrName>, IAdaptableProcessor<NodeGetOrName> {
    override fun calc(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): VirtualType? =
        calc(node.info, node.name, processor, ctx)

    fun calc(info: INodeInfo, name: String, processor: Processor, ctx: ProcessingContext): VirtualType? =
        if (name == "super")
            ctx.body["this"]!!.type()
        else NRGetB.calc(info, name, processor, ctx)

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

    override fun adaptableTo(type: VirtualType, node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): Int {
        val variable = ctx.body.variables
            .stream()
            .filter { it.name == node.name }
            .map { Pair(it, lenArgs(it.type, type)) }
            .sorted { o1, o2 -> o1.second.compareTo(o2.second) }
            .map { it.second }
            .findFirst()
        if (variable.isPresent)
            return variable.get()
        if (node.name == "super")
            return lenArgs(ctx.body["this"]!!.type(), type)
        return lenArgs((ctx.clazz.fields.find { it.name == node.name } ?: return -1).type, type)
    }

    override fun adaptToType(type: VirtualType, node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): NodeGetOrName =
        if (ctx.body.variables.count { it.name == node.name } <= 1)
            node
        else NodeTypedGet.of(node, type)
}