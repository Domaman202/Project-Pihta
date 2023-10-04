package ru.DmN.pht.std.macro.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.processor.ctx.MacroContext
import ru.DmN.pht.std.base.processor.utils.macro
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.base.utils.computeString
import ru.DmN.pht.std.macro.ast.NodeMacroArg
import java.util.*

object NUPMacroArg : INodeUniversalProcessor<NodeMacroArg, NodeMacroArg> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        NPDefault.parse(parser, ctx) { NodeMacroArg(operationToken, it, ctx.macros.reversed()) }

    override fun unparse(node: NodeMacroArg, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        throw UnsupportedOperationException("Not yet implemented")

    override fun calc(node: NodeMacroArg, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(findMacroArgument(node, processor, ctx), ctx)

    override fun process(node: NodeMacroArg, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        processor.process(findMacroArgument(node, processor, ctx), ctx, mode)

    fun findMacroArgument(node: NodeMacroArg, processor: Processor, ctx: ProcessingContext): Node =
        findMacroArgument(ctx.macro, node.uuids, processor.computeString(node.nodes[0], ctx))

    fun findMacroArgument(macro: MacroContext, uuids: List<UUID>, name: String): Node =
        macro.args.entries.find { entry ->
            if (entry.key.second == name)
                uuids.any { it == entry.key.first }
            else false
        }!!.value
}