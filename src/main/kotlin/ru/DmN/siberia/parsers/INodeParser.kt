package ru.DmN.siberia.parsers

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.ast.Node

interface INodeParser {
    fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node?

    fun skip(parser: Parser, ctx: ParsingContext, token: Token): Unit =
        throw UnsupportedOperationException()
}