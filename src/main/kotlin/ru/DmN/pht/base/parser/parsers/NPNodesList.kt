package ru.DmN.pht.base.parser.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node

object NPNodesList : SimpleNP() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        super.parse(parser, ctx, Token(operationToken.line, Token.Type.OPERATION, "progn"))
}