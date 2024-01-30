package ru.DmN.pht.parsers

import ru.DmN.pht.node.NodeParsedTypes
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parser.utils.parseValue
import ru.DmN.siberia.parsers.INodeParser

object NPValnB : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        parse(parser, ctx) { NodeNodesList(INodeInfo.of(NodeParsedTypes.VALN, ctx, token), it) }

    fun parse(parser: Parser, ctx: ParsingContext, constructor: (it: MutableList<Node>) -> Node): Node {
        val nodes = ArrayList<Node>()
        var tk = parser.nextToken()
        while (tk != null && tk.type != Token.DefaultType.CLOSE_CBRACKET) {
            nodes.add(
                if (tk.type == Token.DefaultType.OPEN_BRACKET || tk.type == Token.DefaultType.OPEN_CBRACKET) {
                    parser.tokens.push(tk)
                    parser.parseNode(ctx)!!
                } else parser.parseValue(ctx, tk)
            )
            tk = parser.nextToken()
        }
        return constructor(nodes)
    }
}