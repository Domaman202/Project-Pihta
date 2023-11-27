package ru.DmN.pht.std.parsers

import ru.DmN.siberia.Parser
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.parsers.SimpleNP

object NPMethodCallB : SimpleNP() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val name = parser.parseNode(ctx)!!
        return super.parse(parser, ctx) {
            val list = mutableListOf(it.first())
            list += name
            list += it.drop(1)
            NodeNodesList(Token(operationToken.line, operationToken.type, "mcall"), list)
        }
    }
}