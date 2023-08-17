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
                (get o/value)
            )
            """)
        ).parseNode()?.print())
    }
}