package ru.DmN.pht.parsers

import ru.DmN.pht.ast.NodeGetOrName
import ru.DmN.pht.utils.node.NodeTypes.GET_OR_NAME
import ru.DmN.pht.utils.node.NodeTypes.NAME
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.utils.nextOperation
import ru.DmN.siberia.utils.node.INodeInfo

object NPGetOrName : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? {
        val tk = parser.nextOperation()
        return when (val text = tk.text!!) {
            ".", "super" -> NodeGetOrName(INodeInfo.of(NAME, ctx, token), text, false)
            else -> {
                if (tk.text!!.contains("[/#]".toRegex())) {
                    parser.tokens.push(tk)
                    parser.get(ctx, "get")!!.parse(parser, ctx, Token.operation(token.line, "get!"))
                } else NodeGetOrName(INodeInfo.of(GET_OR_NAME, ctx, token), text, false)
            }
        }
    }
}