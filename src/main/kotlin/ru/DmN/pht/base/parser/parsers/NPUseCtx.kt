package ru.DmN.pht.base.parser.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.NodeUseCtx
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.base.compiler.java.utils.SubList

object NPUseCtx : SimpleNP<NodeUseCtx>() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): NodeUseCtx {
        val names = ArrayList<String>()
        var tk = parser.nextToken()!!
        while (tk.type == Token.Type.OPERATION) {
            names.add(tk.text!!)
            tk = parser.nextToken()!!
        }
        val context = ParsingContext(SubList(ctx.modules), ctx.macros) // todo: sublstack
        names.forEach { Module.MODULES[it]!!.inject(parser, context) }
        return parse(parser, context) { NodeUseCtx(operationToken, names, it) }
    }
}