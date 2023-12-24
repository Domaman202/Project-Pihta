package ru.DmN.pht.std.parsers

import ru.DmN.pht.std.ast.NodeLazySymbol
import ru.DmN.pht.std.node.NodeTypes.LAZY_SYMBOL
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.NodeInfoImpl
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn

object NPLazySymbol : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPProgn.parse(parser, ctx) { NodeLazySymbol(NodeInfoImpl.of(LAZY_SYMBOL, ctx, token), it, null) }
}