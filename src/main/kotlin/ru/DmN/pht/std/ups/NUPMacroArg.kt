package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.ast.NodeMacroArg
import ru.DmN.pht.std.parser.macros
import ru.DmN.pht.std.processor.ctx.MacroContext
import ru.DmN.pht.std.processor.utils.macro
import ru.DmN.pht.std.utils.IStdNUP
import ru.DmN.pht.std.utils.compute
import ru.DmN.pht.std.utils.computeString
import java.util.*

object NUPMacroArg : IStdNUP<NodeMacroArg, NodeMacroArg> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPDefault.parse(parser, ctx) { NodeMacroArg(token, it, ctx.macros.reversed()) }

    override fun calc(node: NodeMacroArg, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(compute(node, processor, ctx), ctx)

    override fun process(node: NodeMacroArg, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        processor.process(compute(node, processor, ctx), ctx, mode)

    override fun compute(node: NodeMacroArg, processor: Processor, ctx: ProcessingContext): Node =
        processor.compute(findMacroArgument(node, processor, ctx), ctx).copy()

    fun findMacroArgument(node: NodeMacroArg, processor: Processor, ctx: ProcessingContext): Node =
        findMacroArgument(ctx.macro, node.uuids, processor.computeString(node.nodes[0], ctx))

    fun findMacroArgument(macro: MacroContext, uuids: List<UUID>, name: String): Node =
        macro.args.entries.find { entry ->
            if (entry.key.second == name)
                uuids.any { it == entry.key.first }
            else false
        }!!.value
}