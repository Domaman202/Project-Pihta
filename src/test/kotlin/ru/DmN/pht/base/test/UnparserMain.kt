package ru.DmN.pht.base.test

import ru.DmN.pht.base.lexer.Lexer
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Unparser

object UnparserMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val node = Parser(
            Lexer("""
            (
                (use std std/math)
                (set this/i (+ this/i 1))
            )
            """)
        ).parseNode()!!
        //
        println("\nParse:\n${node.print()}")
        //
        val unparsed = Unparser().let { it.unparse(node); it.out.toString() }
        println("\nUnparse:\n$unparsed")
        //
        println("\nParse:\n${Parser(Lexer(unparsed)).parseNode()!!.print()}")
    }
}