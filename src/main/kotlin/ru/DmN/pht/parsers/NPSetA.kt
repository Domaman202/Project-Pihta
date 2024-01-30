package ru.DmN.pht.parsers

import ru.DmN.pht.node.NodeTypes
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn

object NPSetA : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val name = parser.nextToken().apply { parser.tokens.push(this) }!!
        return when (name.type) {
            Token.DefaultType.CLASS, Token.DefaultType.OPERATION -> NPSetB.parse(parser, ctx, token)
            else -> NPProgn.parse(parser, ctx) { NodeNodesList(INodeInfo.of(NodeTypes.SET_A, ctx, token), it) }
        }
    }
}