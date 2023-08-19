package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.NodeParser

object NPValn : NodeParser() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val nodes = ArrayList<Node>()
        var tk = parser.nextToken()
        while (tk != null && tk.type != Token.Type.CLOSE_CBRACKET) {
            nodes.add(
                if (tk.type == Token.Type.OPEN_BRACKET || tk.type == Token.Type.OPEN_CBRACKET) {
                    parser.tokens.push(tk)
                    parser.parseNode(ctx)!!
                } else parser.parseValue(ctx, tk)
            )
            tk = parser.nextToken()
        }
        return NodeNodesList(Token(operationToken.line, Token.Type.OPERATION, "valn"), nodes)
    }
}