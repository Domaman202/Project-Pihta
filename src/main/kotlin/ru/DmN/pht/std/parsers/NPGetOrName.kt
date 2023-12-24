package ru.DmN.pht.std.parsers

import ru.DmN.pht.std.ast.NodeGetOrName
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.node.NodeInfoImpl
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.utils.nextOperation

object NPGetOrName : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? {
        val tk = parser.nextOperation()
        return if (tk.text!!.contains("[/#]".toRegex())) {
            parser.tokens.push(tk)
            parser.get(ctx, "get")!!.parse(parser, ctx, Token.operation(token.line, "get!"))
        } else NodeGetOrName(NodeInfoImpl.of(NodeTypes.GET_OR_NAME, ctx, token), tk.text!!, false)
    }
}