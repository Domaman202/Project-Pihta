package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.std.ast.NodeMethodCall
import ru.DmN.pht.base.utils.nextOperation

object NPMethodCallA : SimpleNodeParser<NodeMethodCall>() {
    override fun parse(parser: Parser, operationToken: Token): NodeMethodCall {
        val instance = parser.parseNode()!!
        val name = parser.nextOperation().text!!
        return parse(parser) { NodeMethodCall(operationToken, name, (listOf(instance) + it).toMutableList(), instance.isConst()) }
    }
}