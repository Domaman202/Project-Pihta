package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.pht.std.processors.INodeUniversalProcessor

object NUPComment : INodeUniversalProcessor<Node, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? {
        var ptk: Token? = null
        while (true) {
            val tk = parser.nextToken()!!
            if (tk.type == Token.Type.CLOSE_BRACKET && ptk?.text?.endsWith('\\') != true)
                break
            ptk = tk
        }
        return null
    }
}