package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.std.ast.NodeExFunction
import ru.DmN.pht.std.ast.NodeFunction

object NPExFunction : SimpleNodeParser<NodeFunction>() {
    override fun parse(parser: Parser, operationToken: Token): NodeFunction {
        val clazz = parser.parseNode()!!.getConstValueAsString()
        return NPFunction.parse(parser) { name, rettype, args, nodes -> NodeExFunction(operationToken, clazz, name, rettype, args, nodes) }
    }
}