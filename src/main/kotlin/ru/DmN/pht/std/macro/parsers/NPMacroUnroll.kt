package ru.DmN.pht.std.macro.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.SimpleNP
import ru.DmN.pht.std.macro.ast.NodeMacroUnroll

object NPMacroUnroll : SimpleNP() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        parse(parser, ctx) { NodeMacroUnroll(operationToken, ctx.macros.peek(), it) }
}