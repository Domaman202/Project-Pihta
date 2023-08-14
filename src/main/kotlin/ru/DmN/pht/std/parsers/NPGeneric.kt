package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.base.utils.Generic
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.std.ast.NodeClass
import ru.DmN.pht.std.ast.NodeGeneric

object NPGeneric : SimpleNodeParser<NodeGeneric>() {
    override fun parse(parser: Parser, operationToken: Token): NodeGeneric {
        val name = parser.nextOperation().text!!
        val type = parser.parseNode()!!.getConstValueAsString()
        return parse(parser) { it ->
            it.forEach {
                if (it is NodeClass) {
                    it.generics.list.add(Generic(name, type))
                }
            }
            NodeGeneric(operationToken, it, name, type)
        }
    }
}