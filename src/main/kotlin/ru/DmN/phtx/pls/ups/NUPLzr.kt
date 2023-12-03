package ru.DmN.phtx.pls.ups

import com.kingmang.lazurite.parser.pars.FunctionAdder
import ru.DmN.phtx.pls.processor.utils.convert
import ru.DmN.phtx.pls.utils.Lexer
import ru.DmN.siberia.Parser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.utils.INUP
import com.kingmang.lazurite.parser.pars.Parser as LParser

object NUPLzr : INUP<Node, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val lexer = Lexer(parser.lexer.input, parser.lexer.ptr)
        val lparser = LParser(lexer.tokenize())
        parser.lexer.ptr = lexer.pos
        val stmt = lparser.parse()
        if (lparser.parseErrors.hasErrors())
            throw RuntimeException(lparser.parseErrors.toString())
        stmt.accept(FunctionAdder())
        return convert(token.line, stmt)
    }
}