package ru.DmN.pht.std.macro.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.SimpleNP
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.std.macro.ast.NodeMacroDef

object NPMacroDef : SimpleNP() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val name = parser.nextOperation().text!!
        ctx.macros.push(name)
        return parse(parser, ctx) { NodeMacroDef(operationToken, name, it) }.apply {
            ctx.macros.pop()
        }
    }
}