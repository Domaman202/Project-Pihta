package ru.DmN.pht.parsers

import ru.DmN.pht.ast.NodeLazySymbol
import ru.DmN.pht.utils.node.NodeTypes.LAZY_SYMBOL
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.node.INodeInfo
import java.util.concurrent.atomic.AtomicReference

object NPLazySymbol : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPProgn.parse(parser, ctx) { NodeLazySymbol(INodeInfo.of(LAZY_SYMBOL, ctx, token), it, AtomicReference()) }
}