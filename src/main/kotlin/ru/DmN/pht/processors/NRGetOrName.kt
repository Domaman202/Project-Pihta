package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeGet
import ru.DmN.pht.ast.NodeGet.Type.THIS_FIELD
import ru.DmN.pht.ast.NodeGet.Type.THIS_STATIC_FIELD
import ru.DmN.pht.ast.NodeGetOrName
import ru.DmN.pht.ast.NodeInlBodyA
import ru.DmN.pht.processor.ctx.body
import ru.DmN.pht.processor.ctx.classes
import ru.DmN.pht.processor.ctx.clazz
import ru.DmN.pht.processor.ctx.method
import ru.DmN.pht.processor.utils.Static
import ru.DmN.pht.utils.InlineVariable
import ru.DmN.pht.utils.lenArgs
import ru.DmN.pht.utils.node.NodeTypes.GET_
import ru.DmN.pht.utils.node.NodeTypes.INL_BODY_A
import ru.DmN.pht.utils.node.nodeTypesGet
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

object NRGetOrName : IStdNodeProcessor<NodeGetOrName>, IAdaptableProcessor<NodeGetOrName>, IInlinableProcessor<NodeGetOrName> {
    override fun calc(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): VirtualType? =
        calc(node.info, node.name, processor, ctx)

    fun calc(info: INodeInfo, name: String, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(NRGetB.process(info, name, mutableListOf(), processor, ctx, true)!!, ctx)

    override fun process(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        NRGetB.process(node.info, node.name, mutableListOf(), processor, ctx, valMode)

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
        return when (ctx.body.variables.count {
            if (it.name == node.name)
                if (it is InlineVariable)
                    return node
                else true
            else false
        }) {
            0 -> {
                val clazz = ctx.clazz
                NRGetB.findGetter(node.info, clazz, node.name, emptyList(), !ctx.method.modifiers.static, processor, ctx)?.let { return it }
                val field = clazz.fields.find { it.name == node.name } ?: ctx.classes.asSequence().map { it -> it.fields.find { it.name == node.name } }.first()!!
                NodeGet(
                    node.info.withType(GET_),
                    node.name,
                    if (field.modifiers.isStatic)
                        THIS_STATIC_FIELD
                    else THIS_FIELD,
                    field.type
                )
            }
            1 -> node
            else -> nodeTypesGet(node.info, node.name, type)
        }
    }

    override fun isInlinable(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): Boolean =
        ctx.body[node.name]?.let { it is InlineVariable } == true

    override fun inline(node: NodeGetOrName, processor: Processor, ctx: ProcessingContext): Node {
        val n = (ctx.body[node.name] as InlineVariable).value
        val np = processor.get(n, ctx)
        return NodeInlBodyA(
            node.info.withType(INL_BODY_A),
            mutableListOf(
                if (np is IInlinableProcessor<*> && (np as IInlinableProcessor<Node>).isInlinable(n, processor, ctx))
                    np.inline(n, processor, ctx)
                else n
            ),
            null
        )
    }
}