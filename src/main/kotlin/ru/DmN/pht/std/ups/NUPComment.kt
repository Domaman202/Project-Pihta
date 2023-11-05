package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
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