package ru.DmN.pht.base.parser.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList

open class SimpleNP<T : NodeNodesList> : NodeParser() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): T =
        parse(parser, ctx) { NodeNodesList(operationToken, it) as T }

    fun parse(parser: Parser, ctx: ParsingContext, constructor: (nodes: MutableList<Node>) -> T): T {
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
        parser.tokens.push(tk)
        return constructor.invoke(nodes)
    }

    companion object {
        @JvmStatic
        val INSTANCE = SimpleNP<NodeNodesList>()
    }
}