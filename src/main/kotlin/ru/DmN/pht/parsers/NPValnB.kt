package ru.DmN.pht.parsers

import ru.DmN.pht.utils.node.NodeParsedTypes.VALN
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.Token.DefaultType.*
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parser.utils.parseValue
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.utils.node.INodeInfo

object NPValnB : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        parse(parser, ctx) { NodeNodesList(INodeInfo.of(VALN, ctx, token), it) }

    fun parse(parser: Parser, ctx: ParsingContext, constructor: (it: MutableList<Node>) -> Node): Node {
        val nodes = ArrayList<Node>()
        var tk = parser.nextToken()
        while (tk != null && tk.type != CLOSE_CBRACKET) {
            nodes.add(
                if (tk.type == OPEN_BRACKET || tk.type == OPEN_CBRACKET) {
                    parser.pushToken(tk)
                    parser.parseNode(ctx)!!
                } else parser.parseValue(ctx, tk)
            )
            tk = parser.nextToken()
        }
        return constructor(nodes)
    }
}