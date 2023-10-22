package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.std.processors.INodeUniversalProcessor

object NUPSetA : INodeUniversalProcessor<Node, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? {
        val name = parser.nextToken().apply { parser.tokens.push(this) }!!
        return when (name.type) {
            Token.Type.CLASS, Token.Type.OPERATION -> NUPSetB.parse(parser, ctx, operationToken)
            else -> NPDefault.parse(parser, ctx) { NodeNodesList(operationToken, it) }
        }
    }
}