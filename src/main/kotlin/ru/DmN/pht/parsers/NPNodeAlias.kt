package ru.DmN.pht.parsers

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.Token.DefaultType.OPERATION
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser

class NPNodeAlias(val operation: String) : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        parser.get(ctx, operation)!!.parse(parser, ctx, Token(token.line, token.ptr, OPERATION, operation))
}