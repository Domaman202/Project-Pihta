package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.VTWG
import ru.DmN.pht.utils.OrPair
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.utils.VirtualType

object NRValue : IStdNodeProcessor<NodeValue> {
    override fun calc(node: NodeValue, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType(
            when (node.vtype) {
                NodeValue.Type.NIL    -> "Any"
                NodeValue.Type.BOOLEAN-> "boolean"
                NodeValue.Type.CHAR   -> "char"
                NodeValue.Type.INT    -> "int"
                NodeValue.Type.LONG   -> "long"
                NodeValue.Type.FLOAT  -> "float"
                NodeValue.Type.DOUBLE -> "double"
                NodeValue.Type.STRING,
                NodeValue.Type.NAMING -> "String"
                NodeValue.Type.PRIMITIVE,
                NodeValue.Type.CLASS,
                NodeValue.Type.CLASS_WITH_GEN -> "Class"
            }, processor.tp
        )

    override fun process(node: NodeValue, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        if (node.vtype == NodeValue.Type.CLASS)
            NodeValue(node.info, NodeValue.Type.CLASS, computeType(node, processor, ctx).name)
        else node

    override fun computeInt(node: NodeValue, processor: Processor, ctx: ProcessingContext): Int =
        node.getInt()

    override fun computeString(node: NodeValue, processor: Processor, ctx: ProcessingContext): String =
        node.getString()

    override fun computeType(node: NodeValue, processor: Processor, ctx: ProcessingContext): VirtualType {
        val gs = node.value.indexOf('<')
        if (gs == -1)
            return ctx.global.getType(node.value, processor.tp)
        val gctx = ctx.global
        val type = gctx.getType(node.value.substring(0, gs), processor.tp)
        val iter = type.generics.keys.iterator()
        val generics = HashMap<String, OrPair<VirtualType, String>>()
        var s = node.value.substring(gs)
        while (true) {
            val i = s.indexOf(',')
            generics[iter.next()] =
                if (s[1] == '^')
                    OrPair.first(gctx.getType(s.substring(2, if (i == -1) s.length - 1 else i), processor.tp))
                else OrPair.second(s.substring(1, if (i == -1) s.length - 2 else i - 1))
            if (i == -1)
                break
            s = s.substring(i + 1)
        }
        return VTWG(type, generics)
    }

    override fun computeTypeWithGens(gens: Map<String, VirtualType>, node: NodeValue, processor: Processor, ctx: ProcessingContext): VirtualType {
        val gs = node.value.indexOf('<')
        if (gs == -1)
            return ctx.global.getType(node.value, processor.tp)
        val gctx = ctx.global
        val type = gctx.getType(node.value.substring(0, gs), processor.tp)
        val iter = type.generics.keys.iterator()
        val generics = HashMap<String, OrPair<VirtualType, String>>()
        var s = node.value.substring(gs)
        while (true) {
            val i = s.indexOf(',')
            generics[iter.next()] = OrPair.first(
                if (s.startsWith('^'))
                    gctx.getType(s.substring(2, if (i == -1) s.length - 1 else i), processor.tp)
                else gens[s.substring(1, if (i == -1) s.length - 2 else i - 1)]!!
            )
            if (i == -1)
                break
            s = s.substring(i + 1)
        }
        return VTWG(type, generics)
    }

    override fun computeGenericType(node: NodeValue, processor: Processor, ctx: ProcessingContext): String? =
        if (node.value.endsWith('^'))
            node.value.substring(0, node.value.length - 1)
        else null
}