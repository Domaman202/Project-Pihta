package ru.DmN.pht.parsers

import ru.DmN.pht.utils.node.NodeTypes.SET_A
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.Token.DefaultType.CLASS
import ru.DmN.siberia.lexer.Token.DefaultType.OPERATION
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.NPProgn
import ru.DmN.siberia.utils.node.INodeInfo

object NPSetA : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val name = parser.nextToken().apply { parser.tokens.push(this) }!!
        return when (name.type) {
            CLASS, OPERATION -> NPSetB.parse(parser, ctx, token)
            else -> NPProgn.parse(parser, ctx) { NodeNodesList(INodeInfo.of(SET_A, ctx, token), it) }
        }
    }
}