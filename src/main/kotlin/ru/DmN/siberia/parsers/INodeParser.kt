package ru.DmN.siberia.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.base.ast.Node

interface INodeParser {
    fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node?

    fun skip(parser: Parser, ctx: ParsingContext, token: Token): Unit =
        throw UnsupportedOperationException()
}