package ru.DmN.pht.base.test

import ru.DmN.pht.base.Base
import ru.DmN.pht.base.lexer.Lexer
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.unparser.UnparsingContext

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
        ).parseNode(ParsingContext(mutableListOf(Base)))!!
        //
        println("\nParse:\n${node.print()}")
        //
        val unparsed = Unparser().let { it.unparse(UnparsingContext(mutableListOf(Base)), node); it.out.toString() }
        println("\nUnparse:\n$unparsed")
        //
        println("\nParse:\n${Parser(Lexer(unparsed)).parseNode(ParsingContext(mutableListOf(Base)))!!.print()}")
    }
}