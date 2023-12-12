package ru.DmN.pht.std.parsers

import ru.DmN.siberia.Parser
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.parsers.SimpleNP

object NPValnA : SimpleNP() {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        parse(parser, ctx) { NodeNodesList(Token.operation(token.line, "valn"), it) }
}