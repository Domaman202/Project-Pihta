package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeMacroArg
import ru.DmN.pht.std.parser.macros
import ru.DmN.pht.std.processor.ctx.MacroContext
import ru.DmN.pht.std.processor.utils.macro
import ru.DmN.pht.std.processors.IStdNodeUniversalProcessor
import ru.DmN.pht.std.utils.computeString
import java.util.*

object NUPMacroArg : IStdNodeUniversalProcessor<NodeMacroArg, NodeMacroArg> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        NPDefault.parse(parser, ctx) { NodeMacroArg(operationToken, it, ctx.macros.reversed()) }

    override fun unparse(node: NodeMacroArg, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        throw UnsupportedOperationException("Not yet implemented")

    override fun calc(node: NodeMacroArg, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(compute(node, processor, ctx), ctx)

    override fun process(node: NodeMacroArg, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        processor.process(compute(node, processor, ctx), ctx, mode)

    override fun compute(node: NodeMacroArg, processor: Processor, ctx: ProcessingContext): Node =
        findMacroArgument(node, processor, ctx).copy()

    fun findMacroArgument(node: NodeMacroArg, processor: Processor, ctx: ProcessingContext): Node =
        findMacroArgument(ctx.macro, node.uuids, processor.computeString(node.nodes[0], ctx))

    fun findMacroArgument(macro: MacroContext, uuids: List<UUID>, name: String): Node =
        macro.args.entries.find { entry ->
            if (entry.key.second == name)
                uuids.any { it == entry.key.first }
            else false
        }!!.value
}