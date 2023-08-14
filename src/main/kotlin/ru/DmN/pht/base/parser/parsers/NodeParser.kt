package ru.DmN.pht.base.parser.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ast.Node

abstract class NodeParser {
    abstract fun parse(parser: Parser, operationToken: Token): Node?
}