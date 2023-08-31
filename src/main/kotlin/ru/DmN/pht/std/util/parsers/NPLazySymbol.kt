package ru.DmN.pht.std.util.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.SimpleNP
import ru.DmN.pht.std.util.ast.NodeLazySymbol

object NPLazySymbol : SimpleNP() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        parse(parser, ctx) { NodeLazySymbol(operationToken, it, null) }
}