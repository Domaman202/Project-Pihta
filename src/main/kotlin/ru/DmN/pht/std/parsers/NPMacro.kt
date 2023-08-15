package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.parsers.SimpleNodeParser
import ru.DmN.pht.base.utils.nextNaming
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.std.ast.NodeMacro

object NPMacro : SimpleNodeParser<NodeMacro>() {
    override fun parse(parser: Parser, operationToken: Token): NodeMacro {
        val name = parser.nextOperation().text!!
        return parse(parser) { NodeMacro(operationToken, name, it) }
    }
}