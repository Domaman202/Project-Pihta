package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.base.utils.nextNaming

object NPStaticFieldGet : NodeParser() {
    override fun parse(parser: Parser, operationToken: Token): Node =
        NodeFMGet(operationToken, parser.parseNode()!!, parser.nextNaming().text!!, true)
}