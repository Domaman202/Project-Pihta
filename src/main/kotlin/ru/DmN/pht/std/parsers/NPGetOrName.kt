package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.std.ast.NodeGetOrName

object NPGetOrName : NodeParser() {
    override fun parse(parser: Parser, operationToken: Token): Node? {
        val tk = parser.nextOperation()
        return if (tk.text!!.contains("[/#]".toRegex())) {
            parser.tokens.push(tk)
            parser.parsers["get!"]!!.parse(parser, Token(operationToken.line, Token.Type.OPERATION, "get!"))
        } else NodeGetOrName(operationToken, tk.text, false)
    }
}