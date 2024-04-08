package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeMacroUtil
import ru.DmN.pht.processor.ctx.MacroContext
import ru.DmN.pht.processor.ctx.macro
import ru.DmN.pht.processor.utils.*
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualType
import java.util.*

object NRMacroArg : IStdNodeProcessor<NodeMacroUtil> {
    override fun calc(node: NodeMacroUtil, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(compute(node, processor, ctx), ctx)

    override fun process(node: NodeMacroUtil, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        processor.process(compute(node, processor, ctx), ctx, valMode)

    override fun compute(node: NodeMacroUtil, processor: Processor, ctx: ProcessingContext): Node =
        processor.compute(findMacroArgument(node, processor, ctx), ctx).copy()

    override fun computeInt(node: NodeMacroUtil, processor: Processor, ctx: ProcessingContext): Int? =
        processor.computeIntOr(findMacroArgument(node, processor, ctx), ctx)

    override fun computeString(node: NodeMacroUtil, processor: Processor, ctx: ProcessingContext): String? =
        processor.computeStringOr(findMacroArgument(node, processor, ctx), ctx)

    override fun computeList(node: NodeMacroUtil, processor: Processor, ctx: ProcessingContext): List<Node>? =
        processor.computeListOr(findMacroArgument(node, processor, ctx), ctx)?.map { it.copy() }

    override fun computeType(node: NodeMacroUtil, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.computeTypeOr(findMacroArgument(node, processor, ctx), ctx)

    override fun computeGenericType(node: NodeMacroUtil, processor: Processor, ctx: ProcessingContext): String? =
        processor.computeGenericType(findMacroArgument(node, processor, ctx), ctx)

    private fun findMacroArgument(node: NodeMacroUtil, processor: Processor, ctx: ProcessingContext): Node =
        findMacroArgument(ctx.macro, node.uuids, processor.computeString(node.nodes[0], ctx))

    fun findMacroArgument(macro: MacroContext, uuids: List<UUID>, name: String): Node =
        macro.args.entries.find { entry ->
            if (entry.key.second == name)
                uuids.any { it == entry.key.first }
            else false
        }!!.value
}