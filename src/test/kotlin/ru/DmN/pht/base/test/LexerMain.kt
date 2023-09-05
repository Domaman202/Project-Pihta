package ru.DmN.pht.base.test

import ru.DmN.pht.base.lexer.Lexer

object LexerMain {
    @JvmStatic
    fun main(args: Array<String>) {
        for (token in Lexer(String(LexerMain::class.java.getResourceAsStream("/test.pht").readAllBytes()))) {
            println(token)
        }
    }
}