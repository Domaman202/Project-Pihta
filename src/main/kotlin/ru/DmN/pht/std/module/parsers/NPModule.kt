package ru.DmN.pht.std.module.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NPUse
import ru.DmN.pht.base.parser.parsers.SimpleNP
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.std.module.ast.NodeModule

object NPModule : SimpleNP() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        NodeModule(operationToken, parserModuleArgs(parser)).apply {
            val name = data["name"] as String
            module = Module.MODULES.getOrPut(name) { Module(name) }
            if (!module.init) {
                module.init = true
                (data["files"] as List<String>?)?.let { module.files += it }
                (data["deps"] as List<String>?)?.let {
                    module.deps += it
                    NPUse.process(it, parser, ctx)
                }
            }
        }

    private fun parserModuleArgs(parser: Parser): Map<String, Any> {
        val list = HashMap<String, Any>()
        var tk = parser.nextToken()!!
        while (tk.type != Token.Type.CLOSE_BRACKET) {
            if (tk.type == Token.Type.OPEN_CBRACKET)
                list += parseModuleArg(parser)
            else throw RuntimeException()
            tk = parser.nextToken()!!
        }
        return list
    }

    private fun parseModuleArg(parser: Parser): Pair<String, Any> {
        val key = parser.nextOperation().text!!
        val value = parser.nextToken()!!.let {
            if (it.type == Token.Type.OPEN_CBRACKET)
                parseArgsList(parser)
            else it.text!!
        }
        val end = parser.nextToken()!!
        if (end.type != Token.Type.CLOSE_CBRACKET)
            throw RuntimeException()
        return Pair(key, value)
    }

    private fun parseArgsList(parser: Parser): List<Any> {
        val list = ArrayList<Any>()
        var tk = parser.nextToken()!!
        while (tk.type != Token.Type.CLOSE_CBRACKET) {
            list += tk.text!!
            tk = parser.nextToken()!!
        }
        return list
    }
}   