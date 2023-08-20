package ru.DmN.pht.base.test

import ru.DmN.pht.base.Base
import ru.DmN.pht.base.lexer.Lexer
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.parser.ParsingContext

object ParserMain {
    @JvmStatic
    fun main(args: Array<String>) {
        println(
            Parser(
            Lexer("""
            (
                (use std)
                (tcall 12 21)
            )
            """)
        ).parseNode(ParsingContext(mutableListOf(Base)))?.print())
    }
}