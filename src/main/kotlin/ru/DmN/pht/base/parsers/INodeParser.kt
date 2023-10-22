package ru.DmN.pht.base.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.Node

interface INodeParser {
    fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node?

    fun skip(parser: Parser, ctx: ParsingContext, operationToken: Token): Unit =
        throw UnsupportedOperationException()
}