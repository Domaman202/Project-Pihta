package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.parsers.SimpleNP
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.std.ast.NodeMacro

object NPMacro : SimpleNP<NodeMacro>() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): NodeMacro {
        val name = parser.nextOperation().text!!
        return parse(parser, ctx) { NodeMacro(operationToken, name, it) }
    }
}