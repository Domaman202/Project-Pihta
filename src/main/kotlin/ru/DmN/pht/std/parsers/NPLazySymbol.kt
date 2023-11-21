package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.parsers.SimpleNP
import ru.DmN.pht.std.ast.NodeLazySymbol

object NPLazySymbol : SimpleNP() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        parse(parser, ctx) { NodeLazySymbol(operationToken, it, null) }
}