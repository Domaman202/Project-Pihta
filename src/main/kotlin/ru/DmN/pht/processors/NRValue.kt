package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeValue
import ru.DmN.pht.ast.NodeValue.Type.*
import ru.DmN.pht.jvm.utils.vtype.genericsDefine
import ru.DmN.pht.processor.ctx.getType
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.utils.OrPair
import ru.DmN.pht.utils.vtype.*
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType

object NRValue : IStdNodeProcessor<NodeValue> {
    override fun calc(node: NodeValue, processor: Processor, ctx: ProcessingContext): VirtualType {
        return ctx.global.getType(
            when (node.vtype) {
                NIL             -> return ctx.global.getType("Any").nullableType
                BOOLEAN         -> "boolean"
                CHAR            -> "char"
                INT             -> "int"
                LONG            -> "long"
                FLOAT           -> "float"
                DOUBLE          -> "double"
                STRING,
                NAMING          -> "String"
                PRIMITIVE,
                CLASS,
                CLASS_WITH_GEN  -> "Class"
            }
        )
    }

    override fun process(node: NodeValue, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
        when (node.vtype) {
            PRIMITIVE,
            CLASS,
            CLASS_WITH_GEN -> NodeValue(node.info, CLASS, computeType(node, processor, ctx).name)
            else -> node
        }

    override fun computeInt(node: NodeValue, processor: Processor, ctx: ProcessingContext): Int =
        node.getInt()

    override fun computeString(node: NodeValue, processor: Processor, ctx: ProcessingContext): String =
        node.getString()

    override fun computeType(node: NodeValue, processor: Processor, ctx: ProcessingContext): VirtualType =
        computeType(node.value, processor, ctx)

    fun computeType(value: String, processor: Processor, ctx: ProcessingContext): VirtualType {
        val gs = value.indexOf('<')
        if (gs == -1)
            return ctx.getType(value, processor, ctx)
        val getType = ctx.getType
        val type = getType(value.substring(0, gs), processor, ctx) as PhtVirtualType
        val generics = HashMap<String, OrPair<VirtualType, String>>()
        val iter = type.genericsAccept.iterator()
        var s = value.substring(gs + 1)
        while (true) {
            val i = s.indexOf(' ')
            if (iter.hasNext())
                generics[iter.next()] =
                    if (s[0] == '^')
                        OrPair.first(getType(s.substring(1, if (i == -1) s.length - 1 else i), processor, ctx))
                    else OrPair.second(s.substring(0, if (i == -1) s.length - 2 else i - 1))
            if (i == -1)
                break
            s = s.substring(i + 1)
        }
        return newVVT(type, generics)
    }

    override fun computeTypeWithGens(gens: Map<String, VirtualType>, node: NodeValue, processor: Processor, ctx: ProcessingContext): VirtualType {
        val gs = node.value.indexOf('<')
        if (gs == -1)
            return ctx.getType(node.value, processor, ctx)
        val getType = ctx.getType
        val type = getType(node.value.substring(0, gs), processor, ctx) as PhtVirtualType
        val generics = HashMap<String, OrPair<VirtualType, String>>()
        val iter = type.genericsAccept.iterator()
        var s = node.value.substring(gs + 1)
        while (true) {
            val i = s.indexOf(' ')
            generics[iter.next()] = OrPair.first(
                if (s.startsWith('^'))
                    getType(s.substring(1, if (i == -1) s.length - 1 else i), processor, ctx)
                else gens[s.substring(0, if (i == -1) s.length - 2 else i - 1)]!!
            )
            if (i == -1)
                break
            s = s.substring(i + 1)
        }
        return newVVT(type, generics)
    }

    private fun newVVT(type: PhtVirtualType, generics: MutableMap<String, OrPair<VirtualType, String>>): VVTWithGenerics {
        type.genericsMap.forEach { generics[it.key] = generics[it.value]!! }
        val name = type.name
        type.genericsDefine.keys
            .stream()
            .filter { it.substring(it.indexOf('$') + 1) != name }
            .forEach { gen -> type.genericsDefine[gen]?.let { generics[gen] = OrPair.first(it) } }
        return VVTWithGenerics(type, generics)
    }

    override fun computeGenericType(node: NodeValue, processor: Processor, ctx: ProcessingContext): String? =
        if (node.value.endsWith('^'))
            node.value.substring(0, node.value.length - 1)
        else null

    fun getType(name: String, processor: Processor, ctx: ProcessingContext): VirtualType =
        if (name.length > 2)
            when (name[0]) {
                '?' -> getType(name.substring(1), processor, ctx).nullableType
                '!' -> getType(name.substring(1), processor, ctx).notNullType
                '[' -> getType(name.substring(1), processor, ctx).arrayType
                else -> getType(name, ctx)
            }
        else getType(name, ctx)

    @Suppress("NOTHING_TO_INLINE")
    private inline fun getType(name: String, ctx: ProcessingContext) =
        ctx.global.getType(name).let { it ->
            if (it.genericsDefine.isEmpty())
                it
            else {
                VVTWithGenerics(
                    PhtVirtualType.of(it),
                    it.genericsDefine.map { Pair(it.key, OrPair.first<VirtualType, String>(it.value)) }.toMap()
                )
            }
    }
}