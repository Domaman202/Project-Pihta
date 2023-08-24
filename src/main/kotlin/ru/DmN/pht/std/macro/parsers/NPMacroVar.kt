package ru.DmN.pht.std.macro.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.parsers.SimpleNP
import ru.DmN.pht.std.macro.ast.NodeMacroVar

object NPMacroVar : SimpleNP<NodeMacroVar>() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): NodeMacroVar =
        parse(parser, ctx) { NodeMacroVar(operationToken, ctx.macros.peek(), it) }
}