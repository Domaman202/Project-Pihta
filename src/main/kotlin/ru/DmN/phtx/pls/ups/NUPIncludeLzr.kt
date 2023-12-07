package ru.DmN.phtx.pls.ups

import com.kingmang.lazurite.parser.pars.FunctionAdder
import com.kingmang.lazurite.parser.pars.Lexer
import ru.DmN.phtx.pls.processor.utils.convert
import ru.DmN.phtx.pls.processor.utils.nodePrognB
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.lexer.isNaming
import ru.DmN.siberia.lexer.isOperation
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.utils.INUP

object NUPIncludeLzr : INUP<Node, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? {
        val module = ctx.module
        val line = token.line
        val nodes = parseFileNames(parser)
            .asSequence()
            .map { module.getModuleFile(it) }
            .map { Lexer.tokenize(it) }
            .map { com.kingmang.lazurite.parser.pars.Parser(it) }
            .map { it.parse().apply { if (it.parseErrors.hasErrors()) throw RuntimeException(it.parseErrors.toString()) } }
            .map { it.apply { accept(FunctionAdder()) } }
            .map { convert(line, it) }
            .toMutableList()
        return nodePrognB(line, nodes)
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