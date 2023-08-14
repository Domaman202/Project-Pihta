package ru.DmN.pht.base.parser.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList

open class SimpleNodeParser<T : NodeNodesList> : NodeParser() {
    override fun parse(parser: Parser, operationToken: Token): T {
        return parse(parser) { NodeNodesList(operationToken, it) as T }
    }

    fun parse(parser: Parser, constructor: (nodes: MutableList<Node>) -> T): T {
        val nodes = mutableListOf<Node>()
        var tk = parser.nextToken()
        while (tk != null && tk.type != Token.Type.CLOSE_BRACKET) {
            nodes.add(
                if (tk.type == Token.Type.OPEN_BRACKET) {
                    parser.tokens.push(tk)
                    parser.parseNode()!!
                } else parser.parseValue(tk)
            )
            tk = parser.nextToken()
        }
        parser.tokens.push(tk)
        return constructor.invoke(nodes)
    }

    companion object {
        @JvmStatic
        val INSTANCE = SimpleNodeParser<NodeNodesList>()
    }
}