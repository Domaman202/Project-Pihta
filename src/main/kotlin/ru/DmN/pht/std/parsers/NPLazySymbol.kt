package ru.DmN.pht.std.parsers

import ru.DmN.siberia.Parser
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.parsers.SimpleNP
import ru.DmN.pht.std.ast.NodeLazySymbol

object NPLazySymbol : SimpleNP() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        parse(parser, ctx) { NodeLazySymbol(operationToken, it, null) }
}