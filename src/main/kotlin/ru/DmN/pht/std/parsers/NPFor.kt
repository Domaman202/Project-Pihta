package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.std.ast.NodeFor
import ru.DmN.pht.base.utils.nextOperation

object NPFor : SimpleNodeParser<NodeFor>() {
    override fun parse(parser: Parser, operationToken: Token): NodeFor {
        val name = parser.nextOperation().text!!
        return super.parse(parser) { NodeFor(operationToken, name, it) }
    }
}