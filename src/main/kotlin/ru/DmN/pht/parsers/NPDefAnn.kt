package ru.DmN.pht.parsers

import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser

object NPDefAnn : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        TODO()
    }
}