package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.base.utils.nextNaming
import ru.DmN.pht.std.ast.NodeMethodCall

object NPMethodCallB : SimpleNodeParser<NodeNodesList>() {
    override fun parse(parser: Parser, operationToken: Token): NodeNodesList {
        val name = parser.parseNode()!!
        return super.parse(parser) {
            val list = mutableListOf(it.first())
            list += name
            list += it.drop(1)
            NodeNodesList(Token(operationToken.line, operationToken.type, "mcall"), list)
        }
    }
}