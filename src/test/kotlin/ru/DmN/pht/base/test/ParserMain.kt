package ru.DmN.pht.base.test

import ru.DmN.pht.base.lexer.Lexer
import ru.DmN.pht.base.Parser

object ParserMain {
    @JvmStatic
    fun main(args: Array<String>) {
        println(
            Parser(
            Lexer("""
                (
                    (use std)
                    (def [[a 12][b 21]])
                    (field [[i ^int]])
                )
            """)
        ).parseNode()?.print())
    }
}