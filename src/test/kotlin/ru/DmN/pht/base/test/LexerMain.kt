package ru.DmN.pht.base.test

import ru.DmN.pht.base.lexer.Lexer

object LexerMain {
    @JvmStatic
    fun main(args: Array<String>) {
        for (token in Lexer("""
            (
                (vdef i)
                (set i 12)
                (add i 21)
            )
            """.trimIndent())) {
            println(token)
        }
    }
}