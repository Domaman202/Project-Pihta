package ru.DmN.siberia.parsers

import ru.DmN.siberia.Parser
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.ast.Node

object NPNodesList : SimpleNP() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        super.parse(parser, ctx, Token(operationToken.line, Token.Type.OPERATION, "progn"))
}