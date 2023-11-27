package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.utils.INUP

object NUPSetA : INUP<Node, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val name = parser.nextToken().apply { parser.tokens.push(this) }!!
        return when (name.type) {
            Token.Type.CLASS, Token.Type.OPERATION -> NUPSetB.parse(parser, ctx, token)
            else -> NPDefault.parse(parser, ctx) { NodeNodesList(token, it) }
        }
    }
}