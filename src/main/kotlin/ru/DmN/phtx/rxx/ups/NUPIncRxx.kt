package ru.DmN.phtx.rxx.ups

import ru.DmN.phtx.rxx.lexer.Lexer
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.isOperation
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.utils.INUP
import ru.DmN.phtx.rxx.parser.Parser as RParser

object NUPIncRxx : INUP<Node, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): NodeNodesList {
        val module = ctx.module
        val nodes = ArrayList<Node>()
        parseFileNames(parser)
            .asSequence()
            .map { module.getModuleFile(it) }
            .map { Lexer(it) }
            .map { RParser(it) }
            .map { it.parseNodes() }
            .forEach { nodes += it }
        return nodeProgn(token.line, nodes)
    }

    private fun parseFileNames(parser: Parser): List<String> {
        val names = ArrayList<String>()
        var tk = parser.nextToken()!!
        while (tk.isOperation()) {
            names += tk.text!!
            tk = parser.nextToken()!!
        }
        parser.tokens.push(tk)
        return names
    }
}