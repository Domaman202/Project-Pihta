package ru.DmN.pht.base.parser.parsers

import ru.DmN.pht.base.Base
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeUse
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.module.StdModule

object NPUse : NodeParser() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val names = ArrayList<String>()
        var tk = parser.nextToken()!!
        while (tk.type == Token.Type.OPERATION) {
            names.add(tk.text!!)
            tk = parser.nextToken()!!
        }
        process(names, parser, ctx, ctx)
        return NodeUse(operationToken, names)
    }

    fun process(names: List<String>, parser: Parser, ctx: ParsingContext, context: ParsingContext) {
        names.forEach { name ->
            val module = ctx.modules.find { it.name == name }
            if (module?.init != true)
                Parser(Module.getModuleFile(name)).parseNode(ParsingContext.of(ctx, mutableListOf(Base, StdModule)))
            (module ?: ctx.modules.find { it.name == name }!!).inject(parser, context)
        }
    }
}