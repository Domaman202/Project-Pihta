package ru.DmN.pht.std.base.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.parsers.SimpleNP
import ru.DmN.pht.std.base.ast.NodeMacroUnroll

object NPMacroUnroll : SimpleNP<NodeMacroUnroll>() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): NodeMacroUnroll =
        parse(parser, ctx) { NodeMacroUnroll(operationToken, ctx.macros.peek(), it) }
}