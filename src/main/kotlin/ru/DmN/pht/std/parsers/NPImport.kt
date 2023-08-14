package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.std.ast.NodeImport
import ru.DmN.pht.base.utils.nextOperation

object NPImport : NodeParser() {
    override fun parse(parser: Parser, operationToken: Token): Node {
        return NodeImport(operationToken, parser.nextOperation().text!!)
    }
}