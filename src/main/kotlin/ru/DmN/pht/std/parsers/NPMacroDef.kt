package ru.DmN.pht.std.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.parsers.SimpleNP
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.std.ast.NodeMacroDef

object NPMacroDef : SimpleNP<NodeMacroDef>() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): NodeMacroDef {
        val name = parser.nextOperation().text!!
        ctx.macros.push(name)
        return parse(parser, ctx) { NodeMacroDef(operationToken, name, it) }.apply {
            ctx.macros.pop()
        }
    }
}