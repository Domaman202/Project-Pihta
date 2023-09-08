package ru.DmN.pht.std.base.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.INodeParser
import ru.DmN.pht.std.base.ast.NodeFieldSet
import ru.DmN.pht.base.utils.nextNaming

object NPFieldSet : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val instance = parser.parseNode(ctx)!!
        return NodeFieldSet(operationToken, instance, parser.nextNaming().text!!, parser.parseNode(ctx)!!, instance.isConstClass())
    }
}