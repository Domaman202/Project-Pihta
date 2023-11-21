package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeCompare
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.processors.NRCompare
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPCompare : INodeUniversalProcessor<NodeCompare, NodeCompare> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeCompare, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        NUDefaultX.unparse(node, unparser, ctx, indent)

    override fun calc(node: NodeCompare, processor: Processor, ctx: ProcessingContext): VirtualType =
        NRCompare.calc(node, processor, ctx)
}