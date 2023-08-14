package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser

object NPLet : NodeParser() {
    override fun parse(parser: Parser, operationToken: Token): Node? {
        TODO("Not yet implemented")
    }
}