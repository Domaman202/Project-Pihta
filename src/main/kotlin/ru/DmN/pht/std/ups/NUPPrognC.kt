package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parser.utils.parseValue
import ru.DmN.siberia.utils.INUP

object NUPPrognC : INUP<Node, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        parse(parser, ctx) { NodeNodesList(Token(token.line, Token.Type.OPERATION, "progn"), it) }

    fun parse(parser: Parser, ctx: ParsingContext, constructor: (it: MutableList<Node>) -> Node): Node {
        val nodes = ArrayList<Node>()
        var tk = parser.nextToken()
        while (tk != null && tk.type != Token.Type.CLOSE_BRACKET) {
            nodes.add(
                if (tk.type == Token.Type.OPEN_BRACKET || tk.type == Token.Type.OPEN_CBRACKET) {
                    parser.tokens.push(tk)
                    parser.parseNode(ctx)!!
                } else parser.parseValue(ctx, tk)
            )
            tk = parser.nextToken()
        }
        return constructor(nodes)
    }
}