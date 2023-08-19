package ru.DmN.pht.base.parser.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.NodeNodesList

object NPNodesList : SimpleNP<NodeNodesList>() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): NodeNodesList =
        super.parse(parser, ctx, Token(operationToken.line, Token.Type.OPERATION, "progn"))
}