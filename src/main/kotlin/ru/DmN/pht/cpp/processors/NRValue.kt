package ru.DmN.pht.cpp.processors

import ru.DmN.pht.ast.NodeValue
import ru.DmN.pht.ast.NodeValue.Type.*
import ru.DmN.pht.cpp.utils.vtype.VTNativeClass
import ru.DmN.pht.cpp.utils.vtype.VTString
import ru.DmN.pht.cpp.utils.vtype.VVTAutoPointer
import ru.DmN.pht.cpp.utils.vtype.VVTPointer
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processors.IStdNodeProcessor
import ru.DmN.pht.processors.NRValue
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.pht.utils.vtype.arrayType
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType

object NRValue : IStdNodeProcessor<NodeValue> {
    override fun calc(node: NodeValue, processor: Processor, ctx: ProcessingContext): VirtualType =
        when (node.vtype) {
            NIL             -> VVTPointer(PhtVirtualType.of(VirtualType.VOID))
            BOOLEAN         -> VirtualType.BOOLEAN
            CHAR            -> VirtualType.CHAR
            INT             -> VirtualType.INT
            LONG            -> VirtualType.LONG
            FLOAT           -> VirtualType.FLOAT
            DOUBLE          -> VirtualType.DOUBLE
            STRING          -> VTString
            CLASS,
            CLASS_WITH_GEN  -> VTNativeClass
            else -> throw UnsupportedOperationException()
        }

    override fun process(node: NodeValue, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
        NRValue.process(node, processor, ctx, valMode)

    override fun computeInt(node: NodeValue, processor: Processor, ctx: ProcessingContext): Int =
        NRValue.computeInt(node, processor, ctx)

    override fun computeString(node: NodeValue, processor: Processor, ctx: ProcessingContext): String =
        NRValue.computeString(node, processor, ctx)

    override fun computeType(node: NodeValue, processor: Processor, ctx: ProcessingContext): VirtualType =
        NRValue.computeType(node, processor, ctx)

    override fun computeTypeWithGens(gens: Map<String, VirtualType>, node: NodeValue, processor: Processor, ctx: ProcessingContext): VirtualType =
        NRValue.computeTypeWithGens(gens, node, processor, ctx)

    override fun computeGenericType(node: NodeValue, processor: Processor, ctx: ProcessingContext): String? =
        NRValue.computeGenericType(node, processor, ctx)

    fun getType(name: String, processor: Processor, ctx: ProcessingContext): VirtualType =
        if (name.length > 2)
            when (name.first()) {
                '[' -> ctx.global.getType(name.substring(1)).arrayType
                '$' -> ctx.global.getType(name.substring(1))
                else -> VVTAutoPointer(PhtVirtualType.of(ctx.global.getType(name)))
            }
        else VVTAutoPointer(PhtVirtualType.of(ctx.global.getType(name)))
}