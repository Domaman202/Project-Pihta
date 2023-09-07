package ru.DmN.pht.base.test

import ru.DmN.pht.base.Base
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Lexer
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.unparser.UnparsingContext

object UnparserMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val node = Parser(String(UnparserMain::class.java.getResourceAsStream("/test.pht").readAllBytes())).parseNode(ParsingContext.base())!!
        //
        println("\nParse:\n${node.print()}")
        //
        val unparsed = Unparser().let { it.unparse(node, UnparsingContext(mutableListOf(Base)), 0); it.out.toString() }
        println("\nUnparse:\n$unparsed")
    }
}