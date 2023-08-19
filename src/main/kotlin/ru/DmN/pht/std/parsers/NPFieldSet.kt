package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.std.ast.NodeFieldSet
import ru.DmN.pht.base.utils.nextNaming

object NPFieldSet : NodeParser() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val instance = parser.parseNode(ctx)!!
        return NodeFieldSet(operationToken, instance, parser.nextNaming().text!!, parser.parseNode(ctx)!!, instance.isConstClass())
    }
}