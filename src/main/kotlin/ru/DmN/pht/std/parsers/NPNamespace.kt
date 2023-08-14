package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.std.ast.NodeNamespace
import ru.DmN.pht.base.utils.nextOperation

object NPNamespace : SimpleNodeParser<NodeNamespace>() {
    override fun parse(parser: Parser, operationToken: Token): NodeNamespace {
        val name = parser.nextOperation().text!!
        return parse(parser) { NodeNamespace(operationToken, name, it) }
    }
}