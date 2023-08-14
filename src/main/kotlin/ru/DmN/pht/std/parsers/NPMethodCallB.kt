package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.base.utils.nextNaming
import ru.DmN.pht.std.ast.NodeMethodCall

object NPMethodCallB : SimpleNodeParser<NodeMethodCall>() {
    override fun parse(parser: Parser, operationToken: Token): NodeMethodCall {
        val name = parser.nextNaming().text!!
        return super.parse(parser) {
            NodeMethodCall(
                Token(operationToken.line, operationToken.type, "mcall"),
                name,
                it.toMutableList(),
                it.first().isConst()
            )
        }
    }
}