package ru.DmN.pht.parsers

import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser

object NPComment : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? {
        var ptk: Token? = null
        while (true) {
            val tk = parser.nextToken()!!
            if (tk.type == Token.DefaultType.CLOSE_BRACKET && ptk?.text?.endsWith('\\') != true)
                break
            ptk = tk
        }
        return null
    }
}