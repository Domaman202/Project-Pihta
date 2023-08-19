package ru.DmN.pht.std.math.parsers

import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.std.math.ast.NodeNot

object NPNot : NodeParser() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        NodeNot(operationToken, parser.parseNode(ctx)!!)
}