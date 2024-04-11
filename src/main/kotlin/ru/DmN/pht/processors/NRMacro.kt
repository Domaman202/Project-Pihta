package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeMacro
import ru.DmN.pht.processor.ctx.*
import ru.DmN.pht.processor.utils.compute
import ru.DmN.pht.utils.node.NodeParsedTypes
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.utils.SubMap
import ru.DmN.siberia.utils.exception.MessageException
import ru.DmN.siberia.utils.vtype.VirtualType
import java.util.*

object NRMacro : IStdNodeProcessor<NodeMacro> {
    override fun calc(node: NodeMacro, processor: Processor, ctx: ProcessingContext): VirtualType? {
        val result = macroCalc(node, ctx)
        return macroBodyNode(node, result)?.let { processor.calc(it, result.second) }
    }

    override fun process(node: NodeMacro, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val result = macroCalc(node, ctx)
        return macroBodyNode(node, result)?.let { processor.process(it.copy(), result.second, valMode) }
    }

    override fun compute(node: NodeMacro, processor: Processor, ctx: ProcessingContext): Node {
        val result = macroCalc(node, ctx)
        return macroBodyNode(node, result)?.let { processor.compute(it, result.second) } ?: node
    }

    private fun macroBodyNode(node: NodeMacro, result: Pair<List<Node>, ProcessingContext>): Node? =
        when (result.first.size) {
            0 -> null
            1 -> result.first[0]
            else -> nodeProgn(node.info, result.first.toMutableList())
        }

    private fun macroCalc(node: NodeMacro, ctx: ProcessingContext): Pair<List<Node>, ProcessingContext> {
        val gctx = ctx.global
        //
        val macro = gctx.macros.find { it.name == node.name } ?: throw MessageException(null, "Макрос '${node.name}' не найден!")
        val args = HashMap<Pair<UUID, String>, Node>()
        //
        if (macro.args.size == node.nodes.size)
            macro.args.forEachIndexed { i, it -> args[Pair(macro.uuid, it)] = node.nodes[i] }
        else if (macro.args.isNotEmpty()) {
            macro.args.dropLast(1).forEachIndexed { i, it -> args[Pair(macro.uuid, it)] = node.nodes[i] }
            args[Pair(macro.uuid, macro.args.last())] = NodeNodesList(
                node.info.withType(NodeParsedTypes.VALN),
                node.nodes.drop(args.size - 1).toMutableList() // todo:
            )
        }
        //
        return Pair(
            macro.body,
            ctx.with(macro.ctx.combineWith(gctx)).with(macroCtxOf(ctx, args))
        )
    }

    fun macroCtxOf(ctx: ProcessingContext, args: MutableMap<Pair<UUID, String>, Node>): MacroContext {
        return if (ctx.isMacro())
            MacroContext(SubMap(ctx.macro.args, args))
        else MacroContext(args)
    }
}