package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.std.ast.NodeMacroArg

object NPMacroArg : NodeParser() {
    override fun parse(parser: Parser, operationToken: Token): Node =
        NodeMacroArg(operationToken, parser.nextOperation().text!!)
}