package ru.DmN.pht.std.base.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.SimpleNP

object NPSetA : SimpleNP() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val name = parser.nextToken().apply { parser.tokens.push(this) }!!
        return when (name.type) {
            Token.Type.CLASS, Token.Type.OPERATION -> NPSetB.parse(parser, ctx, operationToken)
            else -> parse(parser, ctx) { NodeNodesList(operationToken, it) }
        }
    }
}