package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.ast.NodeCompare
import ru.DmN.siberia.utils.INUP
import ru.DmN.pht.std.processors.NRCompare
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPCompare : INUP<NodeCompare, NodeCompare> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeCompare, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        NUDefaultX.unparse(node, unparser, ctx, indent)

    override fun calc(node: NodeCompare, processor: Processor, ctx: ProcessingContext): VirtualType =
        NRCompare.calc(node, processor, ctx)
}