package ru.DmN.pht.base.parser.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeUseCtx
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.base.compiler.java.utils.SubList

object NPUseCtx : SimpleNP() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val names = ArrayList<String>()
        var tk = parser.nextToken()!!
        while (tk.type == Token.Type.OPERATION) {
            names.add(tk.text!!)
            tk = parser.nextToken()!!
        }
        parser.tokens.push(tk)
        val context = ParsingContext(SubList(ctx.modules), ctx.macros) // todo: sublstack
        names.forEach { Module.MODULES[it]!!.inject(parser, context) }
        return parse(parser, context) { NodeUseCtx(operationToken, names, it) }
    }
}