package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.utils.INUP

object NUPMCall : INUP<Node, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val name = parser.parseNode(ctx)!!
        return NPDefault.parse(parser, ctx) {
            val list = mutableListOf(it.first())
            list += name
            list += it.drop(1)
            NodeNodesList(Token.operation(token.line, "mcall"), list)
        }
    }
}