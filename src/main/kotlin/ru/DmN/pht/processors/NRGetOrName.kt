package ru.DmN.pht.std.processors

import ru.DmN.pht.ast.NodeTypedGet
import ru.DmN.pht.processor.utils.Static
import ru.DmN.pht.processors.IAdaptableProcessor
import ru.DmN.pht.std.ast.NodeGetA
import ru.DmN.pht.std.ast.NodeGetB
import ru.DmN.pht.std.ast.NodeGetOrName
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.lenArgs
import ru.DmN.pht.utils.InlineVariable
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.utils.VirtualType

object NRGetOrName : IStdNodeProcessor<NodeGetOrName>, IAdaptableProcessor<NodeGetOrName> {
    override fun calc(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): VirtualType? =
        calc(node.info, node.name, processor, ctx)

    fun calc(info: INodeInfo, name: String, processor: Processor, ctx: ProcessingContext): VirtualType? =
        if (name == "super" || name == "this")
            ctx.body["this"]!!.type()
        else processor.calc(NRGetB.process(info, name, mutableListOf(), processor, ctx, ValType.VALUE)!!, ctx)

    override fun process(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        ctx.bodyOrNull?.get(node.name)?.let { if (it is InlineVariable) return processor.process(it.value, ctx, ValType.VALUE) }
        return node // todo:
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
        NRFGetB.findGetter(ctx.clazz, node.name, emptyList(), if (ctx.method.modifiers.static) Static.STATIC else Static.ANY, processor, ctx)?.let { return lenArgs(it.method.rettype, type) }
        return lenArgs((ctx.clazz.fields.find { it.name == node.name } ?: ctx.classes.asSequence().map { it -> it.fields.find { it.name == node.name } }.first() ?: return -1).type, type)
    }

    override fun adaptToType(type: VirtualType, node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): Node {
        val i = ctx.body.variables.count { it.name == node.name }
        return when (i) {
            0 -> {
                val clazz = ctx.clazz
                NRGetB.findGetter(node.info, clazz, node.name, emptyList(), !ctx.method.modifiers.static, processor, ctx)?.let { return it }
                val field = clazz.fields.find { it.name == node.name } ?: ctx.classes.asSequence().map { it -> it.fields.find { it.name == node.name } }.first()!!
                NodeGetB(
                    node.info.withType(NodeTypes.GET_),
                    node.name,
                    if (field.modifiers.isStatic)
                        NodeGetA.Type.THIS_STATIC_FIELD
                    else NodeGetA.Type.THIS_FIELD
                )
            }
            1 -> node
            else -> NodeTypedGet.of(node, type)
        }
    }
}