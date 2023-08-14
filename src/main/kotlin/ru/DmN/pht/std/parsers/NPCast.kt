package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.std.ast.NodeCast
import ru.DmN.pht.base.utils.nextType

object NPCast : NodeParser() {
    override fun parse(parser: Parser, operationToken: Token): Node =
        NodeCast(operationToken, parser.nextType().text!!, parser.parseNode()!!)
}