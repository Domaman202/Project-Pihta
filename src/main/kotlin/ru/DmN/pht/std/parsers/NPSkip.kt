package ru.DmN.pht.std.parsers

import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPDefault

object NPSkip : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? {
        NPDefault.skip(parser, ctx, operationToken)
        return null
    }
}