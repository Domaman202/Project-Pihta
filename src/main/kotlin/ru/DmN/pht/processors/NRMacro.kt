package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeMacro
import ru.DmN.pht.std.node.NodeParsedTypes
import ru.DmN.pht.std.processor.ctx.MacroContext
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.isMacro
import ru.DmN.pht.std.processor.utils.macro
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.utils.compute
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.utils.SubMap
import ru.DmN.siberia.utils.VirtualType
import java.util.*

object NRMacro : IStdNodeProcessor<NodeMacro> {
    override fun calc(node: NodeMacro, processor: Processor, ctx: ProcessingContext): VirtualType? {
        val result = macroCalc(node, ctx)
        return macroBodyNode(node, result)?.let { processor.calc(it, result.second) }
    }

    override fun process(node: NodeMacro, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val result = macroCalc(node, ctx)
        return macroBodyNode(node, result)?.let { processor.process(it.copy(), result.second, mode) }
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
        val macro = gctx.macros.find { it.name == node.name } ?: throw RuntimeException("Macro '${node.name}' not founded!")
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