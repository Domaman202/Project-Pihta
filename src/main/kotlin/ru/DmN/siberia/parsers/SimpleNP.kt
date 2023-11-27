package ru.DmN.siberia.parsers

import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.pht.std.parser.parseValue

/**
 * Парсер-база для парсинга нод с под-нодами.
 */
abstract class SimpleNP : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        parse(parser, ctx) { NodeNodesList(token, it) }

    override fun skip(parser: Parser, ctx: ParsingContext, token: Token) {
        var i = 1
        var tk = parser.nextToken()!!
        while (i > 0) {
            when (tk.type) {
                Token.Type.OPEN_BRACKET -> i++
                Token.Type.CLOSE_BRACKET -> i--
                else -> Unit
            }
            tk = parser.nextToken()!!
        }
        parser.tokens.push(tk)
    }

    /**
     * Парсит ноду с под-нодами.
     * Принимает конструктор ноды, в который передаёт под-ноды.
     */
    fun parse(parser: Parser, ctx: ParsingContext, constructor: (nodes: MutableList<Node>) -> Node): Node {
        val nodes = ArrayList<Node>()
        var tk = parser.nextToken()
        while (tk != null && tk.type != Token.Type.CLOSE_BRACKET) {
            nodes.add(
                if (tk.type == Token.Type.OPEN_BRACKET || tk.type == Token.Type.OPEN_CBRACKET) {
                    parser.tokens.push(tk)
                    parser.parseNode(ctx) ?: continue
                } else parser.parseValue(ctx, tk)
            )
            tk = parser.nextToken()
        }
        parser.tokens.push(tk)
        return constructor.invoke(nodes)
    }
}