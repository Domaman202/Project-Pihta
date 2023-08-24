package ru.DmN.pht.std.macro.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.parsers.NodeParser
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.std.macro.ast.NodeMacroArg

object NPMacroArg : NodeParser() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): NodeMacroArg =
        NodeMacroArg(operationToken, "${parser.nextOperation().text!!}$${ctx.macros.peek()}")
}