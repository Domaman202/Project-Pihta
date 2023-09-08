package ru.DmN.pht.std.base.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.INodeParser
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.std.base.ast.NodeGetOrName

object NPGetOrName : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? {
        val tk = parser.nextOperation()
        return if (tk.text!!.contains("[/#]".toRegex())) {
            parser.tokens.push(tk)
            parser.get(ctx, "get!")!!.parse(parser, ctx, Token(operationToken.line, Token.Type.OPERATION, "get!"))
        } else NodeGetOrName(operationToken, tk.text, false)
    }
}