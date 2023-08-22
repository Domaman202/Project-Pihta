package ru.DmN.pht.test.bf.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser

object NPSimple : NodeParser() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        Node(operationToken)
}