package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.std.ast.NodeClass

object NPJvmSuper : SimpleNodeParser<NodeNodesList>() {
    override fun parse(parser: Parser, operationToken: Token): NodeNodesList {
        return super.parse(parser, operationToken).apply {
            val clazz = nodes.removeFirst().getConstValueAsString()
            nodes.forEach {
                if (it is NodeClass) {
                    it.parents.add(0, clazz)
                }
            }
        }
    }
}