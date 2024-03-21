package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeValue
import ru.DmN.pht.ast.NodeValue.Type.*
import ru.DmN.pht.processor.ctx.GlobalContext
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.utils.OrPair
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.pht.utils.vtype.VVTArray
import ru.DmN.pht.utils.vtype.VVTWithGenerics
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.TypesProvider
import ru.DmN.siberia.utils.vtype.VirtualType

object NRValue : IStdNodeProcessor<NodeValue> {
    override fun calc(node: NodeValue, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType(
            when (node.vtype) {
                NIL             -> "Any"
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
            }, processor.tp
        )

    override fun process(node: NodeValue, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
        if (node.vtype == CLASS)
            NodeValue(node.info, CLASS, computeType(node, processor, ctx).name)
        else node

    override fun computeInt(node: NodeValue, processor: Processor, ctx: ProcessingContext): Int =
        node.getInt()

    override fun computeString(node: NodeValue, processor: Processor, ctx: ProcessingContext): String =
        node.getString()

    override fun computeType(node: NodeValue, processor: Processor, ctx: ProcessingContext): VirtualType =
        computeType(node.value, processor, ctx)

    fun computeType(value: String, processor: Processor, ctx: ProcessingContext): VirtualType {
        val gs = value.indexOf('<')
        if (gs == -1)
            return getType(value, ctx.global, processor.tp)
        val gctx = ctx.global
        val type = getType(value.substring(0, gs), gctx, processor.tp) as PhtVirtualType
        val iter = type.generics.keys.iterator()
        val generics = HashMap<String, OrPair<VirtualType, String>>()
        var s = value.substring(gs)
        while (true) {
            val i = s.indexOf(',')
            generics[iter.next()] =
                if (s[1] == '^')
                    OrPair.first(getType(s.substring(2, if (i == -1) s.length - 1 else i), gctx, processor.tp))
                else OrPair.second(s.substring(1, if (i == -1) s.length - 2 else i - 1))
            if (i == -1)
                break
            s = s.substring(i + 1)
        }
        return VVTWithGenerics(type, generics)
    }

    override fun computeTypeWithGens(gens: Map<String, VirtualType>, node: NodeValue, processor: Processor, ctx: ProcessingContext): VirtualType {
        val gs = node.value.indexOf('<')
        if (gs == -1)
            return getType(node.value, ctx.global, processor.tp)
        val gctx = ctx.global
        val type = getType(node.value.substring(0, gs), gctx, processor.tp) as PhtVirtualType
        val iter = type.generics.keys.iterator()
        val generics = HashMap<String, OrPair<VirtualType, String>>()
        var s = node.value.substring(gs)
        while (true) {
            val i = s.indexOf(',')
            generics[iter.next()] = OrPair.first(
                if (s.startsWith('^'))
                    getType(s.substring(2, if (i == -1) s.length - 1 else i), gctx, processor.tp)
                else gens[s.substring(1, if (i == -1) s.length - 2 else i - 1)]!!
            )
            if (i == -1)
                break
            s = s.substring(i + 1)
        }
        return VVTWithGenerics(type, generics)
    }

    override fun computeGenericType(node: NodeValue, processor: Processor, ctx: ProcessingContext): String? =
        if (node.value.endsWith('^'))
            node.value.substring(0, node.value.length - 1)
        else null

    fun getType(name: String, gctx: GlobalContext, tp: TypesProvider): VirtualType =
        if (name.startsWith('[') && name.length > 2)
            VVTArray(gctx.getType(name.substring(1), tp) as PhtVirtualType)
        else gctx.getType(name, tp)
}